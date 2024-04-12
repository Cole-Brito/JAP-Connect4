/**
 * Connect4
 * Authors: Cole Brito, Paul Squires 
 * Section: 301
 * Professor: Daniel Cormier
 * Last Modified: April 7, 2024
 * Algonquin College CET-CS
 * JAP - Assignment 3-2
 */

package connectfour.model;

/**
 * Types of players used in PlayerManager.
 * TODO: move to local enum in Player or PlayerManager
 */
public enum PlayerType {
	/** Local players are on this client */
	LOCAL,
	/** Network players are on a remote client */
	NETWORK;
}
