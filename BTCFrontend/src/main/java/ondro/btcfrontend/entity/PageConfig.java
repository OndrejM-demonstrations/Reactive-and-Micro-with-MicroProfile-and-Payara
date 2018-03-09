package ondro.btcfrontend.entity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author Ondrej Mihalyi
 */
@Dependent
public class PageConfig {

    @Inject
    @ConfigProperty(name = "page.rate.updateIntervalInMillis", defaultValue = "10000")
    private Long rateUpdateIntervalInMillis;

    public Long getRateUpdateIntervalInMillis() {
        return rateUpdateIntervalInMillis;
    }

    public void setRateUpdateIntervalInMillis(Long rateUpdateIntervalInMillis) {
        this.rateUpdateIntervalInMillis = rateUpdateIntervalInMillis;
    }

}
