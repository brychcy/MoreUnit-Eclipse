package org.moreunit.util;

import org.moreunit.util.BaseTools;

import junit.framework.TestCase;

public class BaseToolsTest extends TestCase {

	public void testGetTestmethodNameFromMethodName() {
		String methodName = "getValue";
		assertEquals("testGetValue", BaseTools.getTestmethodNameFromMethodName(methodName));

		methodName = null;
		assertEquals("", BaseTools.getTestmethodNameFromMethodName(methodName));
		
		methodName = "";
		assertEquals("", BaseTools.getTestmethodNameFromMethodName(methodName));
	}

	public void testGetTestedClass() {
		String className = "Eins";
		String[] prefixes = new String[] { "Test" };
		assertNull(BaseTools.getTestedClass(className, prefixes, new String[0], null, null));
		
		className = "EinsTest";
		String[] suffixes = new String[] { "Test" };
		assertEquals("Eins", BaseTools.getTestedClass(className, new String[0], suffixes, null, null));

		className = "TestCaseDivinerTest";
		suffixes = new String[] { "Test" };
		assertEquals("TestCaseDiviner", BaseTools.getTestedClass(className, new String[0], suffixes, null, null));
		
		className = null;
		assertNull(BaseTools.getTestedClass(className, new String[0], new String[0], null, null));
		
		className = "ABC";
		assertNull(BaseTools.getTestedClass(className, new String[0], new String[0], null, null));
		assertNull(BaseTools.getTestedClass(className, null, null, null, null));
	}
	
	public void testGetTestedClassWithPackagePrefix() throws Exception {
		String className = "test.EinsTest";
		String[] suffixes = new String[] { "Test" };
		String packagePrefix = "test";
		assertEquals("Test with prefix", "Eins", BaseTools.getTestedClass(className, new String[0], suffixes, packagePrefix, null));
		
		className = "EinsTest";
		assertEquals("Test without prefix but package prefix set", "Eins", BaseTools.getTestedClass(className, new String[0], suffixes, packagePrefix, null));
	}
	
	public void testGetTestedClassWithPackageSuffix() throws Exception {
		String className = "test.EinsTest";
		String[] suffixes = new String[] { "Test" };
		String packageSuffix = "test";
		assertEquals("Test with prefix", "Eins", BaseTools.getTestedClass(className, new String[0], suffixes, null, packageSuffix));
		
		className = "EinsTest";
		assertEquals("Test without suffix but package prefix set", "Eins", BaseTools.getTestedClass(className, new String[0], suffixes, null, packageSuffix));
	}

	public void testGetTestedMethod() {
		String methodName = "getValue";
		assertNull(BaseTools.getTestedMethod(methodName));
		
		methodName = "testGetValue";
		assertEquals("getValue", BaseTools.getTestedMethod(methodName));
		
		methodName = null;
		assertNull(BaseTools.getTestedMethod(methodName));
		
		methodName = "test";
		assertEquals(null, BaseTools.getTestedMethod(methodName));
	}

	public void testGetTestMethodNameAfterRename() {
		String methodNameBeforeRename = "countMembers";
		String methodNameAfterRename = "countAllMembers";
		String testMethodName = "testCountMembersSpecialCase";
		
		assertEquals("testCountAllMembersSpecialCase", BaseTools.getTestMethodNameAfterRename(methodNameBeforeRename, methodNameAfterRename, testMethodName));
		
		testMethodName = "testCountMembers";
		assertEquals("testCountAllMembers", BaseTools.getTestMethodNameAfterRename(methodNameBeforeRename, methodNameAfterRename, testMethodName));
	}

	public void testGetStringWithFirstCharToUpperCase() {
		String testString = "hello";
		assertEquals("Hello", BaseTools.getStringWithFirstCharToUpperCase(testString));
		
		assertEquals(null, BaseTools.getStringWithFirstCharToUpperCase(null));
		assertEquals("", BaseTools.getStringWithFirstCharToUpperCase(""));
	}

	public void testRemoveSuffixFromTestCase() {
		String testClassName = "com.my.test.MyTest";
		String packageSuffix = "test";
		
		assertEquals("com.my.MyTest", BaseTools.removeSuffixFromTestCase(testClassName, packageSuffix));
		
		testClassName = "test.MyTest";
		assertEquals("MyTest", BaseTools.removeSuffixFromTestCase(testClassName, packageSuffix));
	}
}