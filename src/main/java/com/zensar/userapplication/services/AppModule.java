package com.zensar.userapplication.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

public class AppModule {
    public static void contributeApplicationDefaults(MappedConfiguration<String,String> configuration) {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,fr,de");
        configuration.add(SymbolConstants.FILE_CHECK_INTERVAL, "10 m");
    }
    public static void bind(ServiceBinder binder){
        binder.bind(ModifyAccountDetailsService.class, ModifyAccountDetailsServiceImpl.class);
        binder.bind(ChangePasswordService.class, ChangePasswordServiceImpl.class);
        binder.bind(ForgotPasswordService.class, ForgotPasswordServiceImpl.class);
        binder.bind(LoginService.class, LoginServiceImpl.class);
        binder.bind(RegisterService.class, RegisterServiceImpl.class);
        binder.bind(ResetPasswordService.class, ResetPasswordServiceImpl.class);
    }
}