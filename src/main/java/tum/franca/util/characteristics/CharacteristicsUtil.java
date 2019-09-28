package tum.franca.util.characteristics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author michaelschott
 *
 */
public class CharacteristicsUtil {
	
	private static final CharacteristicsWrapper p = new CharacteristicsWrapper();

	/**
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllCharacteristicsAsStrings() {
		List<String> list = new ArrayList<String>();
		Class<? extends CharacteristicsWrapper> c = p.getClass();
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
	public static List<List<String>> getAllCharacteristicsAsEnums() {
		List<List<String>> list2 = new ArrayList<List<String>>();
		Class<? extends CharacteristicsWrapper> c = p.getClass();
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
