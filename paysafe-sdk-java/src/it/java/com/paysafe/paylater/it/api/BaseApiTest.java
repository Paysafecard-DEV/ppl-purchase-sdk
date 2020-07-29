package com.paysafe.paylater.it.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;

import com.paysafe.paylater.Factory;
import com.paysafe.paylater.api.LegalDocumentsApi;
import com.paysafe.paylater.api.PurchaseAuthorizationApi;
import com.paysafe.paylater.api.PurchaseLifecycleApi;
import com.paysafe.paylater.communication.Communicator;
import com.paysafe.paylater.logging.SysOutCommunicatorLogger;
import com.paysafe.paylater.model.Address;
import com.paysafe.paylater.model.Amount;
import com.paysafe.paylater.model.AuthorizePurchaseRequest;
import com.paysafe.paylater.model.Consumer;
import com.paysafe.paylater.model.Country;
import com.paysafe.paylater.model.Currency;
import com.paysafe.paylater.model.InitializePurchaseRequest;
import com.paysafe.paylater.model.MethodType;
import com.paysafe.paylater.model.OperationResult;
import com.paysafe.paylater.model.OperationStatus;
import com.paysafe.paylater.model.Person;
import com.paysafe.paylater.model.PurchaseOperationResponse;
import com.paysafe.paylater.model.ResponseWithAuthorization;

public class BaseApiTest {

    public static final String SECRET_KEY_DEFAULT_PROPERTY = "paysafe.paylater.api.secretKey";
    public static final String SECRET_KEY_INSTALLMENTS_PROPERTY = "paysafe.paylater.api.secretKeyInstallments";
    public static final String SECRET_KEY_INVOICE_PROPERTY = "paysafe.paylater.api.secretKeyInvoice";
    public static final String LOGGING_ENABLED_PROPERTY = "paysafe.paylater.api.test.loggingEnabled";
    private static final String PROPERTIES_URL = "/itconfiguration.properties";
    private static final String SECRET_KEY_DEFAULT;
    private static final String SECRET_KEY_INSTALLMENTS;
    private static final String SECRET_KEY_INVOICE;

    private static Communicator communicator;

    static {
        SECRET_KEY_DEFAULT = getProperty(SECRET_KEY_DEFAULT_PROPERTY);
        SECRET_KEY_INSTALLMENTS = getProperty(SECRET_KEY_INSTALLMENTS_PROPERTY);
        SECRET_KEY_INVOICE = getProperty(SECRET_KEY_INVOICE_PROPERTY);
    }

    @BeforeClass
    public static void setUp() {
        communicator = Factory.createCommunicator(getPropertiesFile());
        String loggingValue = getProperty(LOGGING_ENABLED_PROPERTY);
        if (Boolean.parseBoolean(loggingValue)) {
            communicator.enableLogging(SysOutCommunicatorLogger.INSTANCE);
        }
    }

    public static String getDefaultSecretKey() {
        Assume.assumeTrue(
                "No default secret key for was found in the test environment under '" + SECRET_KEY_DEFAULT_PROPERTY + "', this test will be skipped.",
                SECRET_KEY_DEFAULT != null);
        return SECRET_KEY_DEFAULT;
    }

    public static String getSecretKeyInstallments() {
        Assume.assumeTrue(
                "No secret key for installment purchases was found in the test environment under '" + SECRET_KEY_INSTALLMENTS_PROPERTY + "', this test will be skipped.",
                SECRET_KEY_INSTALLMENTS != null);
        return SECRET_KEY_INSTALLMENTS;
    }

    public static String getSecretKeyInvoice() {
        Assume.assumeTrue(
                "No secret key for invoice purchases was found in the test environment under '" + SECRET_KEY_INVOICE_PROPERTY + "', this test will be skipped.",
                SECRET_KEY_INVOICE != null);
        return SECRET_KEY_INVOICE;
    }

    public static LegalDocumentsApi getLegalDocumentsApi() {
        return new LegalDocumentsApi(communicator);
    }

    public static PurchaseAuthorizationApi getPurchaseAuthorizationApi() {
        return new PurchaseAuthorizationApi(communicator);
    }

    @AfterClass
    public static void close() {
        try {
            communicator.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not close communicator", e);
        }
    }

    protected static String getProperty(String propertyKey) {
        String property = System.getProperty(propertyKey);
        return property == null
                ? System.getenv(propertyKey)
                : property;
    }

    protected static URI getPropertiesFile() {
        try {
            return BaseApiTest.class.getResource(PROPERTIES_URL).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Could not load properties file with test data.");
        }
    }

    public PurchaseLifecycleApi getPurchaseLifecycleApi() {
        return new PurchaseLifecycleApi(communicator);
    }

    protected ResponseWithAuthorization<PurchaseOperationResponse> initializePurchase(PurchaseLifecycleApi purchaseLifecycleApi, String secretKey) {
        InitializePurchaseRequest request = new InitializePurchaseRequest()
                .withPurchaseAmount(new Amount(50000L, Currency.EUR))
                .withConsumer(new Consumer()
                        .withEmail("instore-test@paysafe.com")
                        .withPhone("123456789")
                        .withPerson(new Person()
                                .withFirstName("Ernst")
                                .withLastName("Muller")
                                .withBirthdate(LocalDate.parse("1989-08-22")))
                        .withBillingAddress(new Address()
                                .withCountryCode(Country.AT)
                                .withZipCode("5500")
                                .withCity("Bischofshofen")
                                .withStreet("Hauptstrasse")
                                .withHouseNumber("2")));
        return purchaseLifecycleApi.initializePurchase(request, secretKey);
    }

    protected PurchaseOperationResponse authorizePurchase(String purchaseId, String secretKey) {
        AuthorizePurchaseRequest purchaseAuthorizationRequest = new AuthorizePurchaseRequest()
                .withPurchaseId(purchaseId)
                .withMethod(MethodType.URL)
                .withSuccessUrl("https://example.com/successUrl")
                .withCallbackUrl("https://example.com/callbackUrl");

        return getPurchaseAuthorizationApi().authorizePayLater(purchaseAuthorizationRequest, secretKey);
    }

    protected void assertResultIsOk(OperationResult result) {
        Assert.assertNotNull(result);
        Assert.assertEquals(OperationStatus.OK, result.getStatus());
        Assert.assertEquals("0.0.0", result.getStatusCode());
    }
}
