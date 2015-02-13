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
package sep.gaia.resources.locationsearch;

import sep.gaia.resources.DataResource;

/** 
 * Bean class to save the name, the region and the position
 * (i.e. the longitude and latitude) of a location to be searched for.
 * 
 * @author Max Witzelsperger
 */
public class Location extends DataResource {
	/**
	 * The default serial version id.
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 * the name of <code>this</code>.
	 */
	private String name;
	/** 
	 * the region of <code>this</code>.
	 */
	private String region;
	/** 
	 * the position (longitude and latitude) of <code>this</code>.
	 */
	private float[] position;
	
	/**
	 * Makes each instance have its own time
	 */
	private static long time;

	/**
	 * The value to be added to <code>this.timeStamp</code> whenever it
	 * has to be incremented
	 */
	private static final long STEP = 1;

	/**
	 * This method returns the name of <code>this</code>.
	 * 
	 * @return The name of <code>this</code>.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method calls <code>this</code> by its new name.
	 * 
	 * @param name The name how <code>this</code> is called from now on.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method returns the name of the region where <code>this</code>
	 * is situated.
	 * 
	 * @return The name of the region where <code>this</code> is situated.
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * This method sets the name of the region where <code>this</code> is 
	 * situated.
	 * 
	 * @param region The name of the region <code>this</code> is situated.
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * This method returns the position (longitude and latitude)
	 *  of <code>this</code>.
	 * 
	 * @return The position (latitude and longitude) of <code>this</code>.
	 */
	public float[] getPosition() {
		return position;
	}

	/**
	 * This method sets the position (latitude and longitude) of <code>this</code>.
	 * 
	 * @param position The position (longitude and latitude) of 
	 * s<code>this</code> to be set.
	 */
	public void setPosition(float[] position) {
		this.position = position;
	}

	/**
	 * Constructor to save the name, region and position (longitude and latitude)
	 *  of <code>this</code>.
	 * 
	 * @param name The name of <code>this</code>.
	 * 
	 * @param region The region where <code>this</code> is situated.
	 * 
	 * @param position The position (longitude and latitude) of <code>this</code>.
	 */
	public Location(String name, String region, float[] position) {
		this.name = name;
		this.region = region;
		this.position = position;
		time += STEP;
	}
	
	@Override
	protected long incrementTimestamp() {
		
		this.setTimestamp(time);
		time += STEP;
		return time;
	}

	@Override
	public String getKey() {
		return this.name + this.getTimestamp();
	}
}