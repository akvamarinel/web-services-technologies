package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

@Slf4j
public class UDDIServiceManager {

    private static final String CONFIG_FILE = "juddi-client.xml";
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_CRED = "admin";

    public static void registerNewService() throws Exception {
        log.info("Инициализация клиента UDDI для регистрации сервиса...");
        UDDIClient uddiClient = new UDDIClient(CONFIG_FILE);
        Transport transport = uddiClient.getTransport("default");

        UDDISecurityPortType security = transport.getUDDISecurityService();
        UDDIPublicationPortType publish = transport.getUDDIPublishService();

        log.info("Аутентификация в UDDI...");
        String authToken = authenticate(security);

        log.info("Создание и регистрация бизнес-единицы...");
        String businessKey = registerBusiness(publish, authToken);

        log.info("Регистрация нового сервиса с использованием бизнес-ключа: {}", businessKey);
        registerService(publish, authToken, businessKey);
        log.info("Регистрация сервиса успешно завершена.");
    }

    public static void searchAndInvokeExistingService() throws Exception {
        log.info("Инициализация клиента UDDI для поиска сервиса...");
        UDDIClient uddiClient = new UDDIClient(CONFIG_FILE);
        Transport transport = uddiClient.getTransport("default");

        UDDISecurityPortType security = transport.getUDDISecurityService();
        UDDIInquiryPortType inquiry = transport.getUDDIInquiryService();

        log.info("Аутентификация для доступа к сервисам UDDI...");
        String authToken = authenticate(security);

        log.info("Поиск сервиса с именем: 'My  Service'...");
        String serviceKey = findService(inquiry, authToken, "My User service");

        log.info("Получение точки доступа для сервиса с ключом: {}", serviceKey);
        String accessPoint = getServiceAccessPoint(inquiry, authToken, serviceKey);

        log.info("Точка доступа для сервиса найдена: {}", accessPoint);
    }

    private static String authenticate(UDDISecurityPortType security) throws Exception {
        GetAuthToken authRequest = new GetAuthToken();
        authRequest.setUserID(ADMIN_USER);
        authRequest.setCred(ADMIN_CRED);
        AuthToken authToken = security.getAuthToken(authRequest);
        log.info("Аутентификация завершена успешно. Токен получен.");
        return authToken.getAuthInfo();
    }

    private static void registerService(UDDIPublicationPortType publish, String authToken, String businessKey) throws Exception {
        BusinessService service = new BusinessService();
        service.setBusinessKey(businessKey);
        service.getName().add(new Name("My User service", null));

        BindingTemplate bindingTemplate = new BindingTemplate();
        bindingTemplate.setAccessPoint(new AccessPoint("http://localhost:8081/lab1/ws/userService?wsdl", "wsdl"));
        BindingTemplates bindingTemplates = new BindingTemplates();
        bindingTemplates.getBindingTemplate().add(bindingTemplate);
        service.setBindingTemplates(bindingTemplates);

        SaveService saveService = new SaveService();
        saveService.setAuthInfo(authToken);
        saveService.getBusinessService().add(service);

        ServiceDetail serviceDetail = publish.saveService(saveService);
        log.info("Service successfully registered with key: {}", serviceDetail.getBusinessService().get(0).getServiceKey());
    }

    private static String getServiceAccessPoint(UDDIInquiryPortType inquiry, String authToken, String serviceKey) throws Exception {
        GetServiceDetail getServiceDetail = new GetServiceDetail();
        getServiceDetail.setAuthInfo(authToken);
        getServiceDetail.getServiceKey().add(serviceKey);

        ServiceDetail serviceDetail = inquiry.getServiceDetail(getServiceDetail);

        if (serviceDetail.getBusinessService().isEmpty()) {
            log.error("No binding information found for service key: {}", serviceKey);
            throw new Exception("No bindings found for the specified service!");
        }

        BindingTemplates bindingTemplates = serviceDetail.getBusinessService().get(0).getBindingTemplates();
        if (bindingTemplates == null || bindingTemplates.getBindingTemplate().isEmpty()) {
            log.error("Service bindings are missing for key: {}", serviceKey);
            throw new Exception("Service bindings are unavailable!");
        }

        return bindingTemplates.getBindingTemplate().get(0).getAccessPoint().getValue();
    }

    private static String findService(UDDIInquiryPortType inquiry, String authToken, String serviceName) throws Exception {
        FindService findService = new FindService();
        findService.setAuthInfo(authToken);
        findService.getName().add(new Name(serviceName, null));

        ServiceList serviceList = inquiry.findService(findService);
        if (serviceList.getServiceInfos() == null || serviceList.getServiceInfos().getServiceInfo().isEmpty()) {
            log.error("No service found matching the name: {}", serviceName);
            throw new Exception("Service not found!");
        }

        String serviceKey = serviceList.getServiceInfos().getServiceInfo().get(0).getServiceKey();
        log.error("Service located. Key retrieved: {}", serviceKey);
        return serviceKey;
    }

    private static String registerBusiness(UDDIPublicationPortType publish, String authToken) throws Exception {
        BusinessEntity business = new BusinessEntity();
        business.getName().add(new Name("Business", null));

        SaveBusiness saveBusiness = new SaveBusiness();
        saveBusiness.setAuthInfo(authToken);
        saveBusiness.getBusinessEntity().add(business);

        BusinessDetail businessDetail = publish.saveBusiness(saveBusiness);
        String businessKey = businessDetail.getBusinessEntity().get(0).getBusinessKey();
        log.info("Business entity successfully registered with key: {}", businessKey);
        return businessKey;
    }
}
