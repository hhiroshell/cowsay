package com.github.hhiroshell.cowsay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import picocli.CommandLine;

/**
 *
 * @author Rick Brown
 */
public class CowsayTest {

	/**
	 * Maps mode switches to test resources containing expected output.
	 */
	public static Map<String, String> modeMap;

	@BeforeClass
	public static void setUpClass() {
		modeMap = new HashMap<String, String>();
		modeMap.put("b", "cowsayBorg.txt");
		modeMap.put("d", "cowsayDead.txt");
		modeMap.put("g", "cowsayGreedy.txt");
		modeMap.put("p", "cowsayParanoid.txt");
		modeMap.put("s", "cowsayStoned.txt");
		modeMap.put("t", "cowsayTired.txt");
		modeMap.put("w", "cowsayWired.txt");
		modeMap.put("y", "cowsayYoung.txt");
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSay() {
		System.out.println("cowsay Hello");
		String expResult = loadExpected("cowsayHello.txt");
		String[] args = new String[]{"Hello"};
		Cowsay cowsay = new Cowsay();
		new CommandLine(cowsay).parseArgs(args);
		String result = cowsay.say();
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay, with piped input.
	 */
	@Test
	public void testPipedSay() {
		try {
			System.out.println("echo Hello | cowsay");
			String expResult = loadExpected("cowsayHello.txt");
			String[] args = new String[0];
			ByteArrayInputStream in = new ByteArrayInputStream("Hello".getBytes());
			System.setIn(in);
			Cowsay cowsay = new Cowsay();
			new CommandLine(cowsay).parseArgs(args);
			String result = cowsay.say();
			Assert.assertEquals(expResult, result);
		} finally {
			System.setIn(System.in);
		}
	}

	// /**
	//  * Test of say method, of class Cowsay with both piped input and arg.
	//  */
	// @Test
	// public void testPipedSayTrumpedByArgs() {
	// 	try {
	// 		System.out.println("echo foobar | cowsay Hello");
	// 		String[] args = new String[]{"Hello"};
	// 		String expResult = loadExpected("cowsayHello.txt");
	// 		ByteArrayInputStream in = new ByteArrayInputStream("foobar".getBytes());
	// 		System.setIn(in);
	// 		String result = CowsayOrg.say(args);
	// 		Assert.assertEquals("Message from args trumps piped input", expResult, result);
	// 	} finally {
	// 		System.setIn(System.in);
	// 	}
	// }

	// /**
	//  * Test of say method, of class Cowsay, with multiple words in multiple args issue #4.
	//  */
	// @Test
	// public void testSayManyWords() {
	// 	System.out.println("cowsay foo bar baz");
	// 	String[] args = new String[]{"foo", "bar", "baz"};
	// 	String expResult = loadExpected("cowsayFooBarBaz.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay, with multiple words in one arg.
	//  */
	// @Test
	// public void testSayManyWordsOneArg() {
	// 	System.out.println("cowsay foo bar baz");
	// 	String[] args = new String[]{"foo bar baz"};
	// 	String expResult = loadExpected("cowsayFooBarBaz.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay, with multiple words piped.
	//  */
	// @Test
	// public void testPipedSayManyWords() {
	// 	try {
	// 		System.out.println("echo foo bar baz | cowsay");
	// 		String expResult = loadExpected("cowsayFooBarBaz.txt");
	// 		String[] args = new String[0];
	// 		ByteArrayInputStream in = new ByteArrayInputStream("foo bar baz".getBytes());
	// 		System.setIn(in);
	// 		String result = CowsayOrg.say(args);
	// 		Assert.assertEquals(expResult, result);
	// 	} finally {
	// 		System.setIn(System.in);
	// 	}
	// }

	// /**
	//  * Test non-standard cowthink arg.
	//  */
	// @Test
	// public void testCowthinkArg() {
	// 	System.out.println("cowsay --cowthink Hello");
	// 	String[] args = new String[]{"--cowthink", "Hello"};
	// 	String expResult = loadExpected("cowthinkHello.txt");
	// 	CowsayCliOrg.addCowthinkOption();
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay.
	//  */
	// @Test
	// public void testSayWrap2() {
	// 	System.out.println("cowsay -W 2 Hello");
	// 	String[] args = new String[]{"-W", "2", "Hello"};
	// 	String expResult = loadExpected("cowsayWrap2.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay.
	//  */
	// @Test
	// public void testSayNowrap() {
	// 	System.out.println("cowsay -n msg");
	// 	String[] args = new String[]{"-n", "Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
	// 	String expResult = loadExpected("cowsayLong.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay.
	//  */
	// @Test
	// public void testSayWrapZero() {
	// 	System.out.println("cowsay -W 0 msg");
	// 	String[] args = new String[]{"-W", "0", "Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
	// 	String expResult = loadExpected("cowsayLong.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with named cow.
	//  */
	// @Test
	// public void testSayWithNamedFile() {
	// 	System.out.println("cowsay -f tux Hello");
	// 	String[] args = new String[]{"-f", "tux", "Hello"};
	// 	String expResult = loadExpected("cowsayTux.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with named cow - not found.
	//  */
	// @Test
	// public void testSayWithNamedFileNotFound() {
	// 	System.out.println("cowsay -f abcdefgzzzblah Hello");
	// 	String[] args = new String[]{"-f", "abcdefgzzzblah", "Hello"};
	// 	String expResult = loadExpected("cowsayHello.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with modes.
	//  */
	// @Test
	// public void testSayModes() {
	// 	Set<String> modes = modeMap.keySet();
	// 	for (String key : modes) {
	// 		String[] args = new String[]{'-' + key, "Hello"};
	// 		System.out.println("cowsay -" + key + " Hello");
	// 		String expResult = loadExpected(modeMap.get(key));
	// 		String result = CowsayOrg.say(args);
	// 		Assert.assertEquals(expResult, result);
	// 	}
	// }

	// /**
	//  * Test of say method, of class Cowsay, with piped input and flags.
	//  */
	// @Test
	// public void testSayModesPiped() {
	// 	try {
	// 		Set<String> modes = modeMap.keySet();
	// 		for (String key : modes) {
	// 			String[] args = new String[]{'-' + key};
	// 			System.out.println("echo Hello | cowsay -" + key);
	// 			String expResult = loadExpected(modeMap.get(key));
	// 			ByteArrayInputStream in = new ByteArrayInputStream("Hello".getBytes());
	// 			System.setIn(in);
	// 			String result = CowsayOrg.say(args);
	// 			Assert.assertEquals(expResult, result);
	// 		}
	// 	} finally {
	// 		System.setIn(System.in);
	// 	}
	// }


	// /**
	//  * Test of say method, of class Cowsay with custom eyes.
	//  */
	// @Test
	// public void testSayEyes() {
	// 	System.out.println("-e QQ cowsay");
	// 	String[] args = new String[]{"-e", "QQ", "Hello"};
	// 	String expResult = loadExpected("cowsayEyes.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with custom eyes.
	//  */
	// @Test
	// public void testSayEyesDollars() {
	// 	System.out.println("-e $$ cowsay");
	// 	String[] args = new String[]{"-e", "$$", "Hello"};
	// 	String expResult = loadExpected("cowsayGreedy.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with custom eyes.
	//  */
	// @Test
	// public void testSayEyesBackslash() {
	// 	System.out.println("-e \\\\ cowsay");
	// 	String[] args = new String[]{"-e", "\\\\", "Hello"};
	// 	String expResult = loadExpected("cowsayEyesBackslash.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with custom tongue.
	//  */
	// @Test
	// public void testSayTongue() {
	// 	System.out.println("-T V cowsay");
	// 	String[] args = new String[]{"-T", "V", "Hello"};
	// 	String expResult = loadExpected("cowsayTongue.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with custom tongue and eyes.
	//  */
	// @Test
	// public void testSayTongueEyes() {
	// 	System.out.println("cowsay -T V -e QQ Hello");
	// 	String[] args = new String[]{"-T", "V", "-e", "QQ", "Hello"};
	// 	String expResult = loadExpected("cowsayTongueEyes.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with long message.
	//  */
	// @Test
	// public void testSayLongLine() {
	// 	System.out.println("cowsay long");
	// 	String[] args = new String[]{"Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
	// 	String expResult = loadExpected("cowsayMulti.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of think method, of class Cowsay with long message.
	//  */
	// @Test
	// public void testThinkLongLine() {
	// 	System.out.println("cowthink long");
	// 	String[] args = new String[]{"Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
	// 	String expResult = loadExpected("cowthinkMulti.txt");
	// 	String result = CowsayOrg.think(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with multiline input message.
	//  */
	// @Test
	// public void testSayMultiLineInput() {
	// 	System.out.println("cowsay mutli");
	// 	String[] args = new String[]{"This is a test text."
	// 			+ System.getProperty("line.separator") + "To test long lines in cowsay."
	// 			+ System.getProperty("line.separator") + "Moo moo moo."};
	// 	String expResult = loadExpected("cowsayMultiline.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of say method, of class Cowsay with multiline input message.
	//  */
	// @Test
	// public void testSayMultiLineLongInput() {
	// 	System.out.println("cowsay mutli long");
	// 	String[] args = new String[]{"This is a test text." + System.lineSeparator()
	// 			+ "To test long lines in cowsay." + System.lineSeparator()
	// 			+ "This is a really really really really really long line." + System.lineSeparator()
	// 			+ "Moo moo moo."};
	// 	String expResult = loadExpected("cowsayMultilineLong.txt");
	// 	String result = CowsayOrg.say(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test of think method, of class Cowsay.
	//  */
	// @Test
	// public void testThink() {
	// 	System.out.println("cowthink Hello");
	// 	String[] args = new String[]{"Hello"};
	// 	String expResult = loadExpected("cowthinkHello.txt");
	// 	String result = CowsayOrg.think(args);
	// 	Assert.assertEquals(expResult, result);
	// }

	// /**
	//  * Test parsing all known cowfiles.
	//  */
	// @Test
	// public void testAllCowfiles() {
	// 	String[] cowfiles = Cowloader.listAllCowfiles();
	// 	Assert.assertNotEquals(0, cowfiles.length);
	// 	for (String cowfile : cowfiles) {
	// 		System.out.println("cowsay -f " + cowfile + " Hello");
	// 		String[] args = new String[]{"-f", cowfile, "Hello"};
	// 		String result = CowsayOrg.say(args);
	// 		Assert.assertNotEquals("", result);
	// 		System.out.println(result);  // woohoo!
	// 	}
	// }

	/**
	 * Fetches the expected output.
	 * @param name The name of the file to load.
	 * @return The content of the file.
	 */
	public static String loadExpected(final String name) {
		try {
			InputStream expected = CowsayTest.class.getResourceAsStream("/" + name);
			return IOUtils.toString(expected, "UTF-8");
		} catch (IOException ex) {
			Logger.getLogger(CowsayTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
}
