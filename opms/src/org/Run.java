package org;

import org.eredlab.g4.rif.server.G4Server;

/**
 * ÏµÍ³Æô¶¯
 * @author XiongChun
 * @since 2009-01-13
 */
public class Run {
	public static void main(String[] args) {
		G4Server server = new G4Server(
				"D:\\hisoftfm\\out\\artifacts\\hisoftfm_war_exploded",
				"/hisoftfm",
				8899
				);
		server.stop();
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
