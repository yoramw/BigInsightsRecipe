import org.cloudifysource.dsl.context.ServiceContextFactory
import groovy.text.SimpleTemplateEngine

def config = new ConfigSlurper().parse(new File("flume.properties").toURL())
def serviceContext = ServiceContextFactory.getServiceContext()
def instanceID = serviceContext.getInstanceId()
def env = System.getenv()
def hostAddress = env["CLOUDIFY_AGENT_ENV_PRIVATE_IP"];

new AntBuilder().sequential {
	chmod(file:"${serviceContext.serviceDirectory}/set_ssh_key.sh", perm:"ugo+rx")
	exec(executable:"${serviceContext.serviceDirectory}/set_ssh_key.sh", osfamily:"unix") {
		arg("value":"${System.properties["user.home"]}/gs-files/hp-cloud-demo.pem")	
		arg("value":"${System.properties["user.home"]}/.ssh/identity")	
		arg("value":"${config.userPassword}")	
	}
}
println "flume_install.groovy: Ready for flume node installation..."



