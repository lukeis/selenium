package org.openqa.grid.internal.utils.configuration;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridConfiguration extends StandaloneConfiguration {

  @Parameter(
    names = "-cleanUpCycle",
    description = "<Integer> in ms. How often a proxy on the hub will check for timed out thread. Must have timeout option specified also"
  )
  public Integer cleanUpCycle;

  @Parameter(
    names = "-custom",
    description = "comma separated parameters for custom grid extensions. example: -custom myParamA=Value1,myParamB=Value2",
    converter = CustomConverter.class
  )
  public Map<String, String> custom = new HashMap<>();

  @Parameter(
    names = "-host",
    description =  "<IP | hostname> : usually not needed and determined automatically. For exotic network configuration, network with VPN, specifying the host might be necessary."
  )
  public String host;

  @Parameter(
    names = "-maxSession",
    description = "<Integer> max number of tests that can run at the same time on the node, independently of the browser used."
  )
  public Integer maxSession = 1;

  @Parameter(
    names = {"-servlet", "-servlets"},
    description = "list of extra servlets this hub will display. Allows to present custom view of the hub for monitoring and management purpose. Specify multiple on the command line: -servlet tld.company.ServletA -servlet tld.company.ServletB. The servlet will accessible under the path  /grid/admin/ServletA /grid/admin/ServletB"
  )
  public List<String> servlets;

  private class CustomConverter implements IStringConverter<Map<String,String>> {
    @Override
    public Map<String,String> convert(String value) {
      Map<String,String> custom = new HashMap<>();
      for (String pair : value.split(",")) {
        String[] pieces = pair.split("=");
        custom.put(pieces[0], pieces[1]);
      }
      return custom;
    }
  }

  /**
   * replaces this instance of configuration value with the 'other' value if it's set.
   * @param other
   */
  public void merge(GridConfiguration other) {
    super.merge(other);
    if (other.cleanUpCycle != null) cleanUpCycle = other.cleanUpCycle;
    if (other.custom != null) custom = other.custom;
    if (other.host != null) host = other.host;
    if (other.maxSession != 1) maxSession = other.maxSession;
    if (other.servlets != null) servlets = other.servlets;
  }

  @Override
  public String toString(String prefix, String separator, String postfix) {
    StringBuilder sb = new StringBuilder();
    sb.append(super.toString(prefix, separator, postfix));
    sb.append(toString(prefix, separator, postfix, "cleanUpCycle", cleanUpCycle));
    sb.append(toString(prefix, separator, postfix, "custom", custom));
    sb.append(toString(prefix, separator, postfix, "host", host));
    sb.append(toString(prefix, separator, postfix, "maxSession", maxSession));
    sb.append(toString(prefix, separator, postfix, "servlets", servlets));
    return sb.toString();
  }
}
