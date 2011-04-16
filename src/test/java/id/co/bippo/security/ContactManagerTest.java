package id.co.bippo.security;

import javax.inject.Inject;

import junit.framework.Assert;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * Unit test for simple App.
 */
@RunWith(Arquillian.class)
public class ContactManagerTest {

	@Inject ContactManager cm;
	@Inject Subject subject;
	
	/**
	 * Since Arquillian actually creates JAR files under the covers, the @Deployment
	 * is your way of controlling what is included in that Archive. Note, each
	 * class utilized in your test case - whether directly or indirectly - must
	 * be added to the deployment archive.
	 */
	@Deployment
	public static JavaArchive createTestArchive()
	{
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
		.addPackage("id.co.bippo.security")
		//.addClasses(new Class[] {ContactManager.class, SecurityInterceptor.class, SecurityFacade.class})
		.addAsManifestResource("META-INF/beans.xml");
	}

	@Test(expected=AuthorizationException.class)
	public void anonymousCannotView() {
		subject.logout();
		Assert.assertEquals("Hendy Irawan", cm.getName());
	}
	
	@Test
	public void guestCanView() {
		subject.login(new UsernamePasswordToken("guest", "guest"));
		Assert.assertEquals("Hendy Irawan", cm.getName());
	}
	
	@Test(expected=AuthorizationException.class)
	public void guestCannotEdit() {
		subject.login(new UsernamePasswordToken("guest", "guest"));
		cm.setName("Pak Boss");
		Assert.assertEquals("Pak Boss", cm.getName());
	}
	
	@Test
	public void userCanView() {
		subject.login(new UsernamePasswordToken("hendy", "hendy"));
		Assert.assertEquals("Hendy Irawan", cm.getName());
	}
	
	@Test
	public void userCanEdit() {
		subject.login(new UsernamePasswordToken("hendy", "hendy"));
		cm.setName("Pak Boss");
		Assert.assertEquals("Pak Boss", cm.getName());
	}
	
}
