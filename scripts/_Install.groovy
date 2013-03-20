//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//

File appDocFile = new File("${basedir}/grails-app/views/restDocTemplates/appDoc.gsp")
if(appDocFile.exists()) appDocFile=  new File(
	"${basedir}/grails-app/views/restDocTemplates/appDocNewInstall.gsp")
ant.copy(file: "${pluginBasedir}/grails-app/views/restDocTemplates/appDoc.gsp",
	toFile: appDocFile)

File controllerDocFile = new File("${basedir}/grails-app/views/restDocTemplates/controllerDoc.gsp")
if(controllerDocFile.exists()) controllerDocFile= new File(
	"${basedir}/grails-app/views/restDocTemplates/controllerDocNewInstall.gsp")

ant.copy(file: "${pluginBasedir}/grails-app/views/restDocTemplates/controllerDoc.gsp",
	toFile: controllerDocFile)