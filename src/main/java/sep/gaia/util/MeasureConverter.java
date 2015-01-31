/*		
 *		Copyright (c) 2015. 
 *		Johannes Bauer, Fabian Buske, Matthias Fisch,
 *		Michael Mitterer, Maximilian Witzelsperger
 *
 *		Licensed under the Apache License, Version 2.0 (the "License");
 *		you may not use this file except in compliance with the License.
 *		You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *		Unless required by applicable law or agreed to in writing, software
 *		distributed under the License is distributed on an "AS IS" BASIS,
 *		WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *		See the License for the specific language governing permissions and
 *		limitations under the License.
 */
package sep.gaia.util;

/**
 * 
 * Class to provide functions that convert internationally diverting
 * measures.
 * 
 * @author Max Witzelsperger
 *
 */
public final class MeasureConverter {

	private MeasureConverter() { } // utility class constructor
	
	/**
	 * Converts a given temperature in kelvin into the same temperature
	 * in celsius.
	 * 
	 * @param kelvin the given temperature in kelvin
	 * 
	 * @return the temperature in celsius
	 */
	public static double kelvinToCelsius(double kelvin) {
		
		return kelvin - 273.15;
	}
	
	/**
	 * Converts a given temperature in kelvin into the same temperature
	 * in celsius.
	 * 
	 * @param kelvin the given temperature in kelvin
	 * 
	 * @return the temperature in celsius
	 */
	public static float kelvinToCelsius(float kelvin) {
		
		return kelvin - 273.15f;
	}
	
	/**
	 * Converts a given length in feet into the same length in meters.
	 * 
	 * @param feet the given length in feet
	 * 
	 * @return the same length in meters
	 */
	public static double feetToMeter(double feet) {
		
		return feet / 0.3048;
	}
}
