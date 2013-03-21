### Installing and Distributing
To distribute the plugin you navigate to its root directory in a console and run:

     grails package-plugin

This will create a zip file of the plugin starting with grails- then the plugin name and version, in this case grails-rest-doc-plugin-x.x.zip. The package-plugin command will also generate a plugin.xml file which contains machine-readable information about plugin's name, version, author, and so on.

Once you have a plugin distribution file you can navigate to a Grails project and run:

     grails install-plugin /path/to/grails-rest-doc-plugin-x.x.zip

### Documenting the Controllers

_All tags are optional._


#### Controller class tags
* **@infoController** 
* **@descController** 

#### Controller action tags
* **@httpMethod**. (GET, POST, PUT, DELETE). 
* **@param**. The @param tag is followed by the name (not data type) of the parameter, followed by a description of the parameter.
* **@ETag**. The @ETag tag is followed by the name of the ETags (If-None-Match, If-Match), followed by a description of the ETag.
* **@requestHeader**. The @requestHeader tag is followed by the of the http request header, followed by a description of the header.
* **@responseHeader**. The @responseHeader tag is followed by the of the http response header, followed by a description of the header.
* **@urlSufix**. You can put here any value that ends the url (for example '.xml', '.json'), followed by a description.
* **@return**. Description of the data returned by the controller action.

Annotated controller action example:

       /**
     	* Put here a general description of the resource.
     	*
     	* @httpMethod GET
     	* 
     	* @requestHeader headerKey Put here the request header description
     	* @responseHeader headerKey Put here the response header description
     	* 
     	* @urlSufix .xml Put here what happens with this URL sufix
     	* 
     	* @ETag If-None-Match Put here the resource description
     	* @ETag If-Match Put here the resource description  
     	* 
     	* @return Put here the response description
     	*/

[Annotated Controller example](https://github.com/jgzornoza/SistemaVotacion/blob/master/SistemaVotacionControlAcceso/grails-app/controllers/org/sistemavotacion/controlacceso/EventoController.groovy)

### Generatig documentation
Once the plugin has been installed and the Controllers have been annotated, navigate to the project root dir and run:

     grails rest-doc-plugin

### Accesing generated documents
To see all Controllers information start grails application and navigate to:

     http://serverURL/ContextPath/restDoc

To see one concrete Controller information navigate to:

     http://serverURL/ContextPath/controllerName/restDoc

### Customizing the look of generated docs

You can customize the look of the gerated docs modifying the gsp files on dir

     grails-app/views/restDocTemplates

And generating the documents with the new templates.

[Customized docs example.](http://sistemavotacioncontrolacceso.cloudfoundry.com/infoServidor/listaServicios)
