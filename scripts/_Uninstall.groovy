def dir = new File(basedir, "grails-app/views/restDocTemplates")
if (!dir.list()) {
	ant.delete(dir: dir)
}
