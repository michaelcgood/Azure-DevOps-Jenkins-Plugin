package hudson.plugins.tfs.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

/**
 * A class to test {@link UriHelper}.
 */
public class UriHelperTest {

    private QueryString lifeUniverseEverything;

    @Before public void setUp() {
        lifeUniverseEverything = new QueryString();
        lifeUniverseEverything.put("answer", "42");
    }

    private static void assertSame(final String a, final String b) {
        areSame(a, b, true);
    }

    private static void assertNotSame(final String a, final String b) {
        areSame(a, b, false);
    }

    private static void areSame(final String a, final String b, final boolean expected) {
        final URI uriA = a == null ? null : URI.create(a);
        final URI uriB = b == null ? null : URI.create(b);
        final String template = "Expected '%s' and '%s' to be considered%s the same.";
        final String message = String.format(template, a, b, expected ? "" : " NOT");
        Assert.assertEquals(message, expected, UriHelper.areSame(uriA, uriB));
        Assert.assertEquals(message, expected, UriHelper.areSame(uriB, uriA));
    }


    @Test public void areSame_bothNull() throws Exception {
        assertSame(null, null);
    }

    @Test public void areSame_sameInstance() throws Exception {
        final URI uri = URI.create("http://one.example.com");
        Assert.assertTrue(UriHelper.areSame(uri, uri));
    }

    @Test public void areSame_identity() throws Exception {
        assertSame("http://one.example.com", "http://one.example.com");
    }

    @Test public void areSame_endsWithSlash() throws Exception {
        assertSame("http://one.example.com/", "http://one.example.com");
    }

    @Test public void areSame_schemeCase() throws Exception {
        assertSame("http://one.example.com", "HTTP://one.example.com");
    }

    @Test public void areSame_hostCase() throws Exception {
        assertSame("http://ONE.example.com", "http://one.example.com");
    }

    @Test public void areSame_implicitPort() throws Exception {
        assertSame("http://one.example.com", "http://one.example.com:80");
    }

    @Test public void areSame_withPathSlash() throws Exception {
        assertSame("http://one.example.com/path/", "http://one.example.com/path/");
    }

    @Test public void areSame_withPathWithoutSlash() throws Exception {
        assertSame("http://one.example.com/path/", "http://one.example.com/path");
    }

    @Test public void areSame_withPathQuery() throws Exception {
        assertSame("http://one.example.com/search?q=example", "http://one.example.com/search?q=example");
    }

    @Test public void areSame_withPathQueryFragment() throws Exception {
        assertSame("http://one.example.com/search?q=example#top", "http://one.example.com/search?q=example#top");
    }


    @Test public void areSame_oneNull() throws Exception {

        assertNotSame("http://one.example.com/path/", null);
    }

    @Test public void areSame_differentScheme() throws Exception {

        assertNotSame("http://one.example.com/path/", "https://one.example.com/path/");
    }

    @Test public void areSame_differentHost() throws Exception {

        assertNotSame("http://one.example.com/path/", "http://two.example.com/path/");
    }

    @Test public void areSame_differentPort() throws Exception {

        assertNotSame("http://one.example.com/path/", "http://one.example.com:8080/path/");
    }

    @Test public void areSame_differentPath() throws Exception {

        assertNotSame("http://one.example.com/path/", "http://one.example.com/");
    }

    @Test public void areSame_differentQuery() throws Exception {

        assertNotSame("http://one.example.com/path?q=example", "http://one.example.com/path?q=different");
    }

    @Test public void areSame_differentFragment() throws Exception {

        assertNotSame("http://one.example.com/path#top", "http://one.example.com/path#bottom");
    }

    @Test public void areSame_differentFragmentAfterQuery() throws Exception {

        assertNotSame("http://one.example.com/path?q=example#top", "http://one.example.com/path?q=example#bottom");
    }


    private static void assertSameGitRepo(final String a, final String b) {
        areSameGitRepo(a, b, true);
    }

    private static void assertNotSameGitRepo(final String a, final String b) {
        areSameGitRepo(a, b, false);
    }

    private static void areSameGitRepo(final String a, final String b, final boolean expected) {
        final URI uriA = a == null ? null : URI.create(a);
        final URI uriB = b == null ? null : URI.create(b);
        final String template = "Expected '%s' and '%s' to be considered%s the same.";
        final String message = String.format(template, a, b, expected ? "" : " NOT");
        Assert.assertEquals(message, expected, UriHelper.areSameGitRepo(uriA, uriB) );
        Assert.assertEquals(message, expected, UriHelper.areSameGitRepo(uriB, uriA));
    }

    @Test public void areSameGitRepo_withoutDefaultCollection() throws Exception {
        assertSameGitRepo(
                "https://fabrikam-fiber-inc.visualstudio.com/project/_git/repo",
                "https://fabrikam-fiber-inc.visualstudio.com/project/_git/repo"
        );
    }

    @Test public void areSameGitRepo_withDefaultCollection() throws Exception {
        assertSameGitRepo(
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/project/_git/repo",
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/project/_git/repo"
        );
    }

    @Test public void areSameGitRepo_withMixedCaseDefaultCollection() throws Exception {
        assertSameGitRepo(
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/Project/_git/Repo",
                "https://fabrikam-fiber-inc.visualstudio.com/defaultcollection/project/_git/repo"
        );
    }

    @Test public void areSameGitRepo_differentProject() throws Exception {
        assertNotSameGitRepo(
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/myProject/_git/repo",
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/othePproject/_git/repo"
        );
    }

    @Test public void areSameGitRepo_differentRepo() throws Exception {
        assertNotSameGitRepo(
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/project/_git/myRepo",
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/project/_git/otherRepo"
        );
    }

    @Test public void areSameGitRepo_mixTeamServicesDefaultCollection() throws Exception {
        assertSameGitRepo(
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/project/_git/repo",
                "https://fabrikam-fiber-inc.visualstudio.com/project/_git/repo"
        );
    }

    @Test public void areSameGitRepo_mixTfsDefaultCollection() throws Exception {
        assertSameGitRepo(
                "http://tfs.example.com:8080/tfs/DefaultCollection/project/_git/repo",
                "http://tfs.example.com:8080/tfs/project/_git/repo"
        );
    }

    @Test public void areSameGitRepo_differentPorts() throws Exception {
        assertNotSameGitRepo(
                "http://tfs.example.com:8080/tfs/DefaultCollection/project/_git/repo",
                "http://tfs.example.com:8081/tfs/DefaultCollection/project/_git/repo"
        );
    }

    @Test public void areSameGitRepo_teamServicesDifferentProtocols() throws Exception {
        assertSameGitRepo(
                "https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/project/_git/repo",
                "ssh://fabrikam-fiber-inc@fabrikam-fiber-inc.visualstudio.com/project/_git/repo"
        );
    }

    @Test public void areSameGitRepo_tfsDifferentProtocols() throws Exception {
        assertSameGitRepo(
                "http://tfs.example.com:8081/tfs/DefaultCollection/project/_git/repo",
                "ssh://tfs.example.com:22/tfs/project/_git/repo"
        );
    }

    @Test public void hasPath_hostOnly() throws Exception {
        final URI input = URI.create("https://fabrikam-fiber-inc.visualstudio.com");

        final boolean actual = UriHelper.hasPath(input);

        Assert.assertEquals(false, actual);
    }

    @Test public void hasPath_hostSlash() throws Exception {
        final URI input = URI.create("https://fabrikam-fiber-inc.visualstudio.com/");

        final boolean actual = UriHelper.hasPath(input);

        Assert.assertEquals(false, actual);
    }

    @Test public void hasPath_path() throws Exception {
        final URI input = URI.create("https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection");

        final boolean actual = UriHelper.hasPath(input);

        Assert.assertEquals(true, actual);
    }

    @Test public void hasPath_pathSlash() throws Exception {
        final URI input = URI.create("https://fabrikam-fiber-inc.visualstudio.com/DefaultCollection/");

        final boolean actual = UriHelper.hasPath(input);

        Assert.assertEquals(true, actual);
    }


    @Test public void join_uriNoSlash_pathComponents() throws Exception {
        final URI collectionUri = URI.create("https://fabrikam-fiber-inc.visualstudio.com");

        final URI actual = UriHelper.join(collectionUri, "_home", "About");

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/_home/About"), actual);
    }

    @Test public void join_noSlash_pathComponents() throws Exception {
        final String collectionUrl = "https://fabrikam-fiber-inc.visualstudio.com";

        final URI actual = UriHelper.join(collectionUrl, "_home", "About");

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/_home/About"), actual);
    }

    @Test public void join_withSlash_pathComponents() throws Exception {
        final String collectionUrl = "https://fabrikam-fiber-inc.visualstudio.com/";

        final URI actual = UriHelper.join(collectionUrl, "_home", "About");

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/_home/About"), actual);
    }

    @Test public void join_noSlash_queryString() throws Exception {
        final String collectionUrl = "https://fabrikam-fiber-inc.visualstudio.com/";

        final URI actual = UriHelper.join(collectionUrl, lifeUniverseEverything);

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/?answer=42"), actual);
    }

    @Test public void join_withSlash_queryString() throws Exception {
        final String collectionUrl = "https://fabrikam-fiber-inc.visualstudio.com/";

        final URI actual = UriHelper.join(collectionUrl, lifeUniverseEverything);

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/?answer=42"), actual);
    }

    @Test public void join_noSlash_pathAndQueryString() throws Exception {
        final String collectionUrl = "https://fabrikam-fiber-inc.visualstudio.com/";

        final URI actual = UriHelper.join(collectionUrl, "_home", "About", lifeUniverseEverything);

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/_home/About?answer=42"), actual);
    }

    @Test public void join_withSlash_pathAndQueryString() throws Exception {
        final String collectionUrl = "https://fabrikam-fiber-inc.visualstudio.com/";

        final URI actual = UriHelper.join(collectionUrl, "_home", "About", lifeUniverseEverything);

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/_home/About?answer=42"), actual);
    }

    @Test public void join_urlEncoding() throws Exception {
        final String collectionUrl = "https://fabrikam-fiber-inc.visualstudio.com/";

        final URI actual = UriHelper.join(collectionUrl, "_git", "Repo Name+With Spaces");

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/_git/Repo%20Name%2BWith%20Spaces"), actual);
    }

    @Test public void join_sampleApiCall() throws Exception {
        final String collectionUrl = "https://fabrikam-fiber-inc.visualstudio.com/";
        final QueryString qs = new QueryString("api-version", "2.0");

        final URI actual = UriHelper.join(collectionUrl, "_apis", "projects", qs);

        Assert.assertEquals(URI.create("https://fabrikam-fiber-inc.visualstudio.com/_apis/projects?api-version=2.0"), actual);
    }

}
