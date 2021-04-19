package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "classpath:config/driver.properties"})
public interface DriverConfig extends Config {
    @Key("remote.web.user")
    String remoteWebUser();

    @Key("remote.web.password")
    String remoteWebPassword();
}