File appDocFile = new File(basedir, "grails-app/views/restDocTemplates/appDoc.gsp")
if(appDocFile.exists()) appDocFile=  new File(basedir,
	"grails-app/views/restDocTemplates/appDocNewInstall.gsp")
ant.copy(file: "${pluginBasedir}/grails-app/views/restDocTemplates/appDoc.gsp",
	toFile: appDocFile)

File controllerDocFile = new File(basedir, "grails-app/views/restDocTemplates/controllerDoc.gsp")
if(controllerDocFile.exists()) controllerDocFile= new File(basedir,
	"grails-app/views/restDocTemplates/controllerDocNewInstall.gsp")

ant.copy(file: "${pluginBasedir}/grails-app/views/restDocTemplates/controllerDoc.gsp",
	toFile: controllerDocFile)