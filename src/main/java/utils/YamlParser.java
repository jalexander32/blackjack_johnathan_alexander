package utils;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
 /*
  * Re-usable yaml parser to convert a yaml file to a map object
  */
public class YamlParser {
	private String yamlFile;
	private Yaml yaml = new Yaml();
	
	public YamlParser(String yamlFile) {
		this.yamlFile = format(yamlFile);
	}
	/**
	 * 
	 * @return A Map object containing the conversion of the yaml config file
	 */
	public Map<String, Object> getMapping(){
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(this.yamlFile);
		
		return yaml.load(stream);
	}
	/**
	 * Adds the appropriate prefix and suffix to the incoming file name.
	 * @param file
	 * @return A string to the constructor
	 */
	private String format(String file) {
		return "config/" + file + ".yaml";
	}
}
