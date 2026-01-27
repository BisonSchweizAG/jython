package org.python.util;

import static java.nio.file.Files.exists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.python.util.VersionMatchingAntTask.calculateGradleArtefact;
import static org.python.util.VersionMatchingAntTask.checkAntArtefact;
import static org.python.util.VersionMatchingAntTask.unquote;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Location;
import org.junit.Before;
import org.junit.Test;

public class VersionMatchingAntTaskTest {

    private VersionMatchingAntTask task;

    @Before
    public void setUp() throws Exception {
        task = new VersionMatchingAntTask();
        setLocationToBuildXml();
    }

    @Test
    public void testGetLocation() {
        Location location = task.getLocation();
        String fileName = location.getFileName();
        assertTrue(fileName.endsWith("build.xml"));
    }

    @Test
    public void testParseGradleAretfacts() throws IOException {
        Set<String> gradleArtefacts = task.parseGradleArtefacts();
        assertNotNull(gradleArtefacts);
        assertEquals(20, gradleArtefacts.size());
    }

    @Test
    public void testParseAntArtefacts() throws IOException {
        Set<String> antArtefacts = task.parseAntArtefacts();
        assertNotNull(antArtefacts);
        assertEquals(31, antArtefacts.size());
    }

    @Test
    public void testUnquote() {
        assertEquals("x", unquote("\"x"));
        assertEquals("x", unquote("x\""));
        assertEquals("x", unquote("\"x\""));
        assertEquals("", unquote("\"\""));
    }

    @Test
    public void testUnquoteNoAction() {
        assertEquals("x", unquote("x"));
        assertEquals("'x'", unquote("'x'"));
        assertEquals(null, unquote(null));
    }

    @Test
    public void testCalculateGradleArtefact() {
        try {
            calculateGradleArtefact("rev_unknown", "1.2.3");
        } catch (BuildException be) {
            assertEquals("Missing artefact name format for 'rev_unknown'", be.getMessage());
        }
        assertEquals("antlr-complete-5.4.3.jar", calculateGradleArtefact("rev_antlr", "5.4.3"));
        assertEquals("asm-9.8.7.jar", calculateGradleArtefact("rev_asm", "9.8.7"));
        assertEquals("bcpkix-jdk18on-8.7.6.jar", calculateGradleArtefact("rev_bouncycastle", "8.7.6"));
        assertEquals("commons-compress-78.1.jar", calculateGradleArtefact("rev_commons_compress", "78.1"));
        assertEquals("commons-io-1.2.3.4.jar", calculateGradleArtefact("rev_commons_io", "1.2.3.4"));
        assertEquals("guava-33.3.3-jre.jar", calculateGradleArtefact("rev_guava", "33.3.3-jre"));
        assertEquals("icu4j-78.7.jar", calculateGradleArtefact("rev_icu4j", "78.7"));
        assertEquals("jdbc-15.3.2.1.jar", calculateGradleArtefact("rev_informix", "15.3.2.1"));
        assertEquals("jakartaee-api-11.0.jar", calculateGradleArtefact("rev_jakartaee_api", "11.0"));
        assertEquals("java-sizeof-0.1.2.jar", calculateGradleArtefact("rev_java_sizeof", "0.1.2"));
        assertEquals("jffi-1.3.15.jar", calculateGradleArtefact("rev_jffi", "1.3.15"));
        assertEquals("jline-2.14.6.jar", calculateGradleArtefact("rev_jline", "2.14.6"));
        assertEquals("jnr-constants-0.10.4.jar", calculateGradleArtefact("rev_jnr_constants", "0.10.4"));
        assertEquals("jnr-ffi-2.2.18.jar", calculateGradleArtefact("rev_jnr_ffi", "2.2.18"));
        assertEquals("jnr-netdb-1.2.3.jar", calculateGradleArtefact("rev_jnr_netdb", "1.2.3"));
        assertEquals("jnr-posix-3.20.10.jar", calculateGradleArtefact("rev_jnr_posix", "3.20.10"));
        assertEquals("junit-4.13.2.jar", calculateGradleArtefact("rev_junit", "4.13.2"));
        assertEquals("netty-buffer-5.4.3.Final.jar", calculateGradleArtefact("rev_netty", "5.4.3.Final"));
        assertEquals("ojdbc8-23.26.1.2.3.jar", calculateGradleArtefact("rev_oracle", "23.26.1.2.3"));
    }

    @Test
    public void testCheckAntArtefact() {
        Set<String> antArtefacts = new HashSet<>();
        antArtefacts.add("junit-4.jar");
        antArtefacts.add("additiona.jar");
        antArtefacts.add("asm-9.8.7.jar");

        checkAntArtefact("junit-4.jar", antArtefacts);
        checkAntArtefact("asm-9.8.7.jar", antArtefacts);
        try {
            checkAntArtefact("icu4j-78.1.jar", antArtefacts);
        } catch (BuildException be) {
            assertEquals("Gradle artefact 'icu4j-78.1.jar' is missing from build.xml. Please synchronize extlibs and build.xml with libs.versions.toml.",
                            be.getMessage());
        }
    }

    private void setLocationToBuildXml() {
        // in test mode, we need to set the location to the build.xml file
        URL actualURL = VersionMatchingAntTask.class.getResource("VersionMatchingAntTask.class");
        assertNotNull(actualURL);
        Path actualPath = Paths.get(actualURL.getFile());
        Path repo = actualPath.getParent().getParent().getParent().getParent().getParent().getParent();
        Path buildXml = repo.resolve("build.xml");
        if (exists(buildXml)) {
            // this is the Eclipse location
        } else {
            repo = repo.getParent().getParent();
            buildXml = repo.resolve("build.xml");
            // this is the gradle command line location
            assertTrue(exists(buildXml));
        }
        task.setLocation(new Location(buildXml.toString()));
    }

}
