package tum.franca.properties;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tum.franca.properties.PropertiesWrapper.Properties;

/**
 * 
 * @author michaelschott
 *
 */
public class PropertiesUtil {
	
	private static final PropertiesWrapper p = new PropertiesWrapper();

	/**
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllPropertiesAsStrings() {
		List<String> list = new ArrayList<String>();
		Class<? extends PropertiesWrapper> c = p.getClass();
		Field[] fields = c.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				list.add((String) fields[i].get(c));
				fields[i].getClass();
			} catch (IllegalArgumentException | IllegalAccessException  | ClassCastException e) {
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @return List<List<String>>
	 */
	public static List<List<String>> getAllPropertiesAsEnums() {
		List<List<String>> list2 = new ArrayList<List<String>>();
		Class<? extends PropertiesWrapper> c = p.getClass();
		Class<?>[] innerClasses = c.getDeclaredClasses();
		Class<?>[] innerEnums = innerClasses[0].getClasses();
		for (int i = 0; i < innerEnums.length; i++) {
			List<String> list = new ArrayList<>();
			list2.add(list);
			list.add(innerEnums[i].getSimpleName());
			Object[] objects = innerEnums[i].getEnumConstants();
			for (int j = 0; j < objects.length; j++) {
				list.add(objects[j].toString());
			}
		}
		
		return list2;
	}

}
