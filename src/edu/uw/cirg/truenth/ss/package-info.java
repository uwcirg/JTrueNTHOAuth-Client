/**
 * SS packages: artifacts to manipulate SS' data.
 * 
 * <p>
 * The SS packages define artifacts that are uniquely used to interpret information coming from SS. 
 * As such, client applications can use those transition elements to retrieve data in an organized fashion, 
 * which is independent from the transmission format; e.g. JSON. 
 * </p>
 * 
 * <p>
 * These packages define complementary capabilities to the OAuth functionalities and their use is not required. 
 * If the developer decides to define their own implementation, 
 * they can choose to use the OAuth classes and ignore the elements defined here.
 * </p>
 * 
 * <p>
 * For instance, JSON may be used to receive information, 
 * however, the OAuth library can deliver a Java Bean to its clients. 
 * Additionally, application developers can decide to use extractors to obtain pieces of data instead.
 * </p>
 * 
 * @author Victor de Lima Soares
 * @since Apr 14, 2016
 * 
 */
package edu.uw.cirg.truenth.ss;