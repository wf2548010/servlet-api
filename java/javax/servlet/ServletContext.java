/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.servlet;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * Defines a set of methods that a servlet uses to communicate with its
 * servlet container, for example, to get the MIME type of a file, dispatch
 * requests, or write to a log file.
 *
 * 定义servlet用来与servlet容器通信的一组方法。例如，用于获取文件的MIME类、派发请求或写入日志文件。
 *
 * <p>There is one context per "web application" per Java Virtual Machine.  (A
 * "web application" is a collection of servlets and content installed under a
 * specific subset of the server's URL namespace such as <code>/catalog</code>
 * and possibly installed via a <code>.war</code> file.) 
 *
 * 每一个Java虚拟机每一个WEB应用程序都由一个上下文
 * WEB应用是安装在服务器下的URL命名空间里的servlets和内容的集合
 *
 * <p>In the case of a web
 * application marked "distributed" in its deployment descriptor, there will
 * be one context instance for each virtual machine.  In this situation, the 
 * context cannot be used as a location to share global information (because
 * the information won't be truly global).  Use an external resource like 
 * a database instead.
 *
 * 在部署描述符中标记为分布式的WEB应用程序的情况下，每个虚拟机都有一个上下文实例。
 * 在这种情况下，上下文不能用作共享全局信息的位置(因为细腻些不是真正的全局信息)，使用外部资源。比如数据库
 *
 * <p>The <code>ServletContext</code> object is contained within 
 * the {@link ServletConfig} object, which the Web server provides the
 * servlet when the servlet is initialized.
 *
 * @author 	Various
 *
 * @see 	Servlet#getServletConfig
 * @see 	ServletConfig#getServletContext
 */

public interface ServletContext {

    /**
     * The name of the <tt>ServletContext</tt> attribute which stores
     * the private temporary directory (of type <tt>java.io.File</tt>)
     * provided by the servlet container for the <tt>ServletContext</tt>
     *
     * Servlet上下文的名称，该属性存储Servlet上下文容器提供的私有临时目录（类型java.io.File）
     *
     */
    public static final String TEMPDIR = "javax.servlet.context.tempdir";


    /**
     * The name of the <code>ServletContext</code> attribute whose value
     * (of type <code>java.util.List&lt;java.lang.String&gt;</code>) contains
     * the list of names of JAR files in <code>WEB-INF/lib</code> ordered by
     * their web fragment names (with possible exclusions if
     * <code>&lt;absolute-ordering&gt;</code> without any
     * <code>&lt;others/&gt;</code> is being used), or null if no
     * absolute or relative ordering has been specified
     *
     * Servlet上下文属性的名称，其值（类型java.utils.List和java.lang.String等）包含由网络片段名
     * 排序的WEB-INF/lib中的JAR文件名的列表（如果不适用任何其他的绝对排序，则可能使用除外），或者
     * 如果没有指定绝对或相对排序，则为NULL
     *
     */
    public static final String ORDERED_LIBS =
        "javax.servlet.context.orderedLibs";


    /**
     * Returns the context path of the web application.
     *
     * 返回WEB应用程序的上下文路径。
     *
     * <p>The context path is the portion of the request URI that is used
     * to select the context of the request. The context path always comes
     * first in a request URI. If this context is the “default” context
     * rooted at the base of the Web server’s URL name space, this path
     * will be an empty string. Otherwise, if the context is not rooted at
     * the root of the server’s name space, the path starts with a /
     * character but does not end with a / character.
     *
     * 上下文路径是URI的一部分，用于选择请求的上下文。
     * 在请求上下文URI中，上下文路径总是排在第一位。
     * 如果此上下文位于WEB服务器URL名称的默认上下文，则此路径将是空字符串
     * 否则，如果上下文不是位于服务器控件的高更路径，则以/字符开始。但不以/结束
     *
     *
     * <p>It is possible that a servlet container may match a context by
     * more than one context path. In such cases the
     * {@link javax.servlet.http.HttpServletRequest#getContextPath()}
     * will return the actual context path used by the request and it may
     * differ from the path returned by this method.
     * The context path returned by this method should be considered as the
     * prime or preferred context path of the application.
     *
     * Servlet容器可以通过多个上下文路径来匹配上下文。
     * 在{javax.servlet.http.HttpServletRequest#getContextpath()}这个方法中将返回请求的实际上下文路径
     * 并且可能与本类中的getContextPath方法返回的路径不同。
     * 本方法返回的上下文路径应被视为应用程序的主要或首选上下文路径
     *
     * @return The context path of the web application, or "" for the
     * default (root) context
     *
     * 应用程序的上下文路径，当为根路径时返回""
     *
     * @see javax.servlet.http.HttpServletRequest#getContextPath()
     *
     * @since Servlet 2.5
     */
    public String getContextPath();


    /**
     * Returns a <code>ServletContext</code> object that 
     * corresponds to a specified URL on the server.
     *
     * 返回对应于服务器上指定URL的ServletContext对象
     *
     *
     * <p>This method allows servlets to gain
     * access to the context for various parts of the server, and as
     * needed obtain {@link RequestDispatcher} objects from the context.
     * The given path must be begin with <tt>/</tt>, is interpreted relative 
     * to the server's document root and is matched against the context
     * roots of other web applications hosted on this container.
     *
     * 此方法允许Servlet访问服务器各个部分的上下文，并根据需要从上下文中获取{RequestDispatcher}对象
     * 路径入参必须以/开始，可以理解为是服务器根路径。并且与托管在该容器上的其他web应用程序的上下文根路径匹配
     *
     *
     * <p>In a security conscious environment, the servlet container may
     * return <code>null</code> for a given URL.
     *
     * 在安全意识环境中，servlet容器可以为给定的URL返回NULL
     *
     * @param uripath 	a <code>String</code> specifying the context path of
     *			another web application in the container.
     *
     * 指定容器中另一个WEB应用程序的上下文路径的字符串
     *
     * @return		the <code>ServletContext</code> object that
     *			corresponds to the named URL, or null if either
			none exists or the container wishes to restrict 
     * 			this access.
     *
     * 对应于URL命名的ServletContext对象，或者如果不存在或者容器希望限制此访问，则为空。
     *
     * @see 		RequestDispatcher
     */
    public ServletContext getContext(String uripath);
    

    /**
     * Returns the major version of the Servlet API that this
     * servlet container supports. All implementations that comply
     * with Version 3.0 must have this method return the integer 3.
     *
     * 返回此Servlet容器支持的Servlet API的主要版本号
     * 所有符合版本3.0的实现都必须让此方法返回整数3
     *
     * @return 3
     */
    public int getMajorVersion();
    
    
    /**
     * Returns the minor version of the Servlet API that this
     * servlet container supports. All implementations that comply
     * with Version 3.0 must have this method return the integer 0.
     *
     * 返回此servlet容器支持的Servlet API的次要版本。
     * 所有符合3.0版本的实现都必须让此方法返回整数0
     *
     * @return 0
     */
    public int getMinorVersion();
    
   
    /**
     * Gets the major version of the Servlet specification that the
     * application represented by this ServletContext is based on.
     *
     * 获取此ServletContext表示的应用程序所基于的Servlet规范的主要版本
     *
     *
     * <p>The value returned may be different from {@link #getMajorVersion},
     * which returns the major version of the Servlet specification
     * supported by the Servlet container.
     * 返回的值可能与{getMajorVersio}你不同，后者返回Servlet容器支持的Servlet规范的只要版本。
     *
     * @return the major version of the Servlet specification that the
     * application represented by this ServletContext is based on
     *
     * ServletContext表示的应用程序所基于的Servlet规范的主要版本
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * 如果此ServletContext被传递给ServletContextListener的contextInitialized方法
     * 没有在web.xml或者web-frasgment.xml中声明，也没有使用{javax.servlet.annotation.WebListener}注解
     * 将抛出UnsupportedOperationException
     *
     * @since Servlet 3.0
     */
    public int getEffectiveMajorVersion();
    
    
    /**
     * Gets the minor version of the Servlet specification that the
     * application represented by this ServletContext is based on.
     *
     * 获取ServletContext标识的应用程序所给予的Servlet规范的次要版本
     *
     * <p>The value returned may be different from {@link #getMinorVersion},
     * which returns the minor version of the Servlet specification
     * supported by the Servlet container.
     *
     * 返回的值与{getMainorVersion}方法返回的次要版本号不同，后者返回Servelt容器支持的Servlet规范的次要版本号
     *
     * @return the minor version of the Servlet specification that the
     * application represented by this ServletContext is based on
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public int getEffectiveMinorVersion();


    /**
     * Returns the MIME type of the specified file, or <code>null</code> if 
     * the MIME type is not known. The MIME type is determined
     * by the configuration of the servlet container, and may be specified
     * in a web application deployment descriptor. Common MIME
     * types include <code>text/html</code> and <code>image/gif</code>.
     *
     * 返回指定文件的MINE类型，如果MIME类型未知的话返回NULL。
     * MIME类型是由Servlet容器的配置决定的。并且可以在WEB应用程序部署描述符中指定
     * 一般MINE类型包括{text/html}和{image/gif}
     *
     * @param file a <code>String</code> specifying the name of a file
     *
     *文件字符串名称
     *
     * @return a <code>String</code> specifying the file's MIME type
     *
     * 文件的MIME类型字符串
     *
     */
    public String getMimeType(String file);
    

    /**
     * Returns a directory-like listing of all the paths to resources
     * within the web application whose longest sub-path matches the
     * supplied path argument.
     *
     * 返回WEB应用程序中所有资源路径的目录式列表，其最长子路径与所提供的路径参数匹配。
     *
     * <p>Paths indicating subdirectory paths end with a <tt>/</tt>.
     *
     * 指示子目录路径的路径以/结尾
     *
     * <p>The returned paths are all relative to the root of the web
         * application, or relative to the <tt>/META-INF/resources</tt>
         * directory of a JAR file inside the web application's
         * <tt>/WEB-INF/lib</tt> directory, and have a leading <tt>/</tt>.
     *
     * 返回的路径都与WEB应用程序的根路径有关，或者与WEB应用程序的/WEB-INF/lib目录内的JAR文件的/META-INF/resources目录有关，并且具有前导/
     *
     * <p>For example, for a web application containing:
     *
     * <code><pre>
     *   /welcome.html
     *   /catalog/index.html
     *   /catalog/products.html
     *   /catalog/offers/books.html
     *   /catalog/offers/music.html
     *   /customer/login.jsp
     *   /WEB-INF/web.xml
     *   /WEB-INF/classes/com.acme.OrderServlet.class
     *   /WEB-INF/lib/catalog.jar!/META-INF/resources/catalog/moreOffers/books.html
     * </pre></code>
     * 
     * <tt>getResourcePaths("/")</tt> would return
     * <tt>{"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}</tt>,
     * and <tt>getResourcePaths("/catalog/")</tt> would return
     * <tt>{"/catalog/index.html", "/catalog/products.html",
     * "/catalog/offers/", "/catalog/moreOffers/"}</tt>.
     * 
     * @param path the partial path used to match the resources,
     * which must start with a <tt>/</tt>
     * @return a Set containing the directory listing, or null if there
     * are no resources in the web application whose path
     * begins with the supplied path.
     * 
     * @since Servlet 2.3
     */    
    public Set<String> getResourcePaths(String path);
    

    /**
     * Returns a URL to the resource that is mapped to the given path.
     *
     * 返回映射到给定路径的资源的URL
     *
     * <p>The path must begin with a <tt>/</tt> and is interpreted
     * as relative to the current context root,
     * or relative to the <tt>/META-INF/resources</tt> directory
     * of a JAR file inside the web application's <tt>/WEB-INF/lib</tt>
     * directory.
     * This method will first search the document root of the
     * web application for the requested resource, before searching
     * any of the JAR files inside <tt>/WEB-INF/lib</tt>.
     * The order in which the JAR files inside <tt>/WEB-INF/lib</tt>
     * are searched is undefined.
     *
     * 在搜索/WEB-INF/lib中的任何JAR文件之前，此方法将首先搜索web应用程序的文档更路径以查找请求的资源。
     * 搜索/WEB-INF/lib中的JAR文件的顺序未定义
     *
     * <p>This method allows the servlet container to make a resource 
     * available to servlets from any source. Resources 
     * can be located on a local or remote
     * file system, in a database, or in a <code>.war</code> file. 
     *
     * 此方法允许servlet容器为来自任何源的servlet提供可用的资源
     * 资源文件可以是本地的、远程系统的、数据库或者WAR文件中
     *
     * <p>The servlet container must implement the URL handlers
     * and <code>URLConnection</code> objects that are necessary
     * to access the resource.
     *
     * Servlet容器必须实现访问资源所需的URL处理程序和URLConnection对象
     *
     * <p>This method returns <code>null</code>
     * if no resource is mapped to the pathname.
     *
     * <p>Some containers may allow writing to the URL returned by
     * this method using the methods of the URL class.
     *
     * <p>The resource content is returned directly, so be aware that 
     * requesting a <code>.jsp</code> page returns the JSP source code.
     * Use a <code>RequestDispatcher</code> instead to include results of 
     * an execution.
     *
     * <p>This method has a different purpose than
     * <code>java.lang.Class.getResource</code>,
     * which looks up resources based on a class loader. This
     * method does not use class loaders.
     * 
     * @param path a <code>String</code> specifying
     * the path to the resource
     *
     * @return the resource located at the named path,
     * or <code>null</code> if there is no resource at that path
     *
     * @exception MalformedURLException if the pathname is not given in 
     * the correct form
     */    
    public URL getResource(String path) throws MalformedURLException;
    

    /**
     * Returns the resource located at the named path as
     * an <code>InputStream</code> object.
     *
     * 将位于路径的资源以输入流对象返回
     *
     * <p>The data in the <code>InputStream</code> can be 
     * of any type or length. The path must be specified according
     * to the rules given in <code>getResource</code>.
     * This method returns <code>null</code> if no resource exists at
     * the specified path. 
     * 
     * <p>Meta-information such as content length and content type
     * that is available via <code>getResource</code>
     * method is lost when using this method.
     *
     * <p>The servlet container must implement the URL handlers
     * and <code>URLConnection</code> objects necessary to access
     * the resource.
     *
     * <p>This method is different from 
     * <code>java.lang.Class.getResourceAsStream</code>,
     * which uses a class loader. This method allows servlet containers 
     * to make a resource available
     * to a servlet from any location, without using a class loader.
     * 
     *
     * @param path 	a <code>String</code> specifying the path
     *			to the resource
     *
     * @return 		the <code>InputStream</code> returned to the 
     *			servlet, or <code>null</code> if no resource
     *			exists at the specified path 
     */
    public InputStream getResourceAsStream(String path);
    

    /**
     * 
     * Returns a {@link RequestDispatcher} object that acts
     * as a wrapper for the resource located at the given path.
     * A <code>RequestDispatcher</code> object can be used to forward 
     * a request to the resource or to include the resource in a response.
     * The resource can be dynamic or static.
     *
     * 返回{@link RequestDispatcher}对象，该对象充当位于给定路径的资源的包装器。
     *
     * <p>The pathname must begin with a <tt>/</tt> and is interpreted as
     * relative to the current context root.  Use <code>getContext</code>
     * to obtain a <code>RequestDispatcher</code> for resources in foreign
     * contexts.
     *
     * <p>This method returns <code>null</code> if the
     * <code>ServletContext</code> cannot return a
     * <code>RequestDispatcher</code>.
     *
     * @param path 	a <code>String</code> specifying the pathname
     *			to the resource
     *
     * @return 		a <code>RequestDispatcher</code> object
     *			that acts as a wrapper for the resource
     *			at the specified path, or <code>null</code> if 
     *			the <code>ServletContext</code> cannot return
     *			a <code>RequestDispatcher</code>
     *
     * @see 		RequestDispatcher
     * @see 		ServletContext#getContext
     */
    public RequestDispatcher getRequestDispatcher(String path);


    /**
     * Returns a {@link RequestDispatcher} object that acts
     * as a wrapper for the named servlet.
     *
     * 返回充当命名servlet的包装器的{@link RequestDispatcher}对象。
     *
     * <p>Servlets (and JSP pages also) may be given names via server 
     * administration or via a web application deployment descriptor.
     * A servlet instance can determine its name using 
     * {@link ServletConfig#getServletName}.
     *
     * <p>This method returns <code>null</code> if the 
     * <code>ServletContext</code>
     * cannot return a <code>RequestDispatcher</code> for any reason.
     *
     * @param name 	a <code>String</code> specifying the name
     *			of a servlet to wrap
     *
     * @return 		a <code>RequestDispatcher</code> object
     *			that acts as a wrapper for the named servlet,
     *			or <code>null</code> if the <code>ServletContext</code>
     *			cannot return a <code>RequestDispatcher</code>
     *
     * @see 		RequestDispatcher
     * @see 		ServletContext#getContext
     * @see 		ServletConfig#getServletName
     */
    public RequestDispatcher getNamedDispatcher(String name);
    
    
    /**
     * @deprecated	As of Java Servlet API 2.1, with no direct replacement.
     *
     * <p>This method was originally defined to retrieve a servlet
     * from a <code>ServletContext</code>. In this version, this method 
     * always returns <code>null</code> and remains only to preserve 
     * binary compatibility. This method will be permanently removed 
     * in a future version of the Java Servlet API.
     *
     * 最初定义此方法是为了从ServletContext检索servlet。
     * 在这个版本中，这个方法总是返回<code>null</code>，并且只保持二进制兼容性。此方法将在Java Servlet API的未来版本中永久删除。
     *
     * <p>In lieu of this method, servlets can share information using the 
     * <code>ServletContext</code> class and can perform shared business logic
     * by invoking methods on common non-servlet classes.
     */
    public Servlet getServlet(String name) throws ServletException;
    

    /**
     * @deprecated	As of Java Servlet API 2.0, with no replacement.
     *
     * <p>This method was originally defined to return an
     * <code>Enumeration</code> of all the servlets known to this servlet
     * context.
     * In this version, this method always returns an empty enumeration and
     * remains only to preserve binary compatibility. This method
     * will be permanently removed in a future version of the Java
     * Servlet API.
     *
     * 最初定义此方法是为了返回此servlet上下文已知的所有servlet的枚举。
     * 在这个版本中，这个方法总是返回一个空枚举，并且只保持二进制兼容性。此方法将在Java Servlet API的未来版本中永久删除。
     */
    public Enumeration<Servlet> getServlets();
    

    /**
     * @deprecated	As of Java Servlet API 2.1, with no replacement.
     *
     * <p>This method was originally defined to return an 
     * <code>Enumeration</code>
     * of all the servlet names known to this context. In this version,
     * this method always returns an empty <code>Enumeration</code> and 
     * remains only to preserve binary compatibility. This method will 
     * be permanently removed in a future version of the Java Servlet API.
     *
     * 最初定义此方法是为了返回上下文已知的所有servlet名称的枚举。
     * 在这个版本中，这个方法总是返回一个空的<code>Enumeration</code>，并且只保持二进制兼容性。此方法将在Java Servlet API的未来版本中永久删除。
     *
     */
    public Enumeration<String> getServletNames();
    

    /**
     *
     * Writes the specified message to a servlet log file, usually
     * an event log. The name and type of the servlet log file is 
     * specific to the servlet container.
     *
     * 将指定的消息写入servlet日志文件，通常是事件日志。servlet日志文件的名称和类型是特定于servlet容器的。
     *
     * @param msg 	a <code>String</code> specifying the 
     *			message to be written to the log file
     */
    public void log(String msg);
    

    /**
     * @deprecated	As of Java Servlet API 2.1, use
     * 			{@link #log(String message, Throwable throwable)} 
     *			instead.
     *
     * <p>This method was originally defined to write an 
     * exception's stack trace and an explanatory error message
     * to the servlet log file.
     *
     * 最初定义此方法是为了将异常的堆栈跟踪和解释性错误消息写入servlet日志文件。
     *
     */
    public void log(Exception exception, String msg);
    

    /**
     * Writes an explanatory message and a stack trace
     * for a given <code>Throwable</code> exception
     * to the servlet log file. The name and type of the servlet log 
     * file is specific to the servlet container, usually an event log.
     *
     * 将针对给定Throwable异常的解释性消息和堆栈跟踪写入servlet日志文件。servlet日志文件的名称和类型特定于servlet容器，通常是事件日志。
     *
     * @param message 		a <code>String</code> that 
     *				describes the error or exception
     *
     * @param throwable 	the <code>Throwable</code> error 
     *				or exception
     */
    public void log(String message, Throwable throwable);
    
    
    /**
     * Gets the <i>real</i> path corresponding to the given
     * <i>virtual</i> path.
     *
     * 获取与给定虚拟路径对应的真实路径
     *
     * <p>For example, if <tt>path</tt> is equal to <tt>/index.html</tt>,
     * this method will return the absolute file path on the server's
     * filesystem to which a request of the form
     * <tt>http://&lt;host&gt;:&lt;port&gt;/&lt;contextPath&gt;/index.html</tt>
     * would be mapped, where <tt>&lt;contextPath&gt;</tt> corresponds to the
     * context path of this ServletContext.
     *
     * <p>The real path returned will be in a form
     * appropriate to the computer and operating system on
     * which the servlet container is running, including the
     * proper path separators.
     *
     * <p>Resources inside the <tt>/META-INF/resources</tt>
     * directories of JAR files bundled in the application's
     * <tt>/WEB-INF/lib</tt> directory must be considered only if the
     * container has unpacked them from their containing JAR file, in
     * which case the path to the unpacked location must be returned.
     *
     * <p>This method returns <code>null</code> if the servlet container
     * is unable to translate the given <i>virtual</i> path to a
     * <i>real</i> path.
     *
     * @param path the <i>virtual</i> path to be translated to a
     * <i>real</i> path
     *
     * @return the <i>real</i> path, or <tt>null</tt> if the
     * translation cannot be performed
     */
    public String getRealPath(String path);
    

    /**
     * Returns the name and version of the servlet container on which
     * the servlet is running.
     *
     * 返回servlet正在运行的servlet容器的名称和版本。
     *
     * <p>The form of the returned string is 
     * <i>servername</i>/<i>versionnumber</i>.
     * For example, the JavaServer Web Development Kit may return the string
     * <code>JavaServer Web Dev Kit/1.0</code>.
     *
     * <p>The servlet container may return other optional information 
     * after the primary string in parentheses, for example,
     * <code>JavaServer Web Dev Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86)</code>.
     *
     *
     * @return 		a <code>String</code> containing at least the 
     *			servlet container name and version number
     */
    public String getServerInfo();
    

    /**
     * Returns a <code>String</code> containing the value of the named
     * context-wide initialization parameter, or <code>null</code> if the 
     * parameter does not exist.
     *
     * <p>This method can make available configuration information useful
     * to an entire web application.  For example, it can provide a 
     * webmaster's email address or the name of a system that holds 
     * critical data.
     *
     * @param	name	a <code>String</code> containing the name of the
     *                  parameter whose value is requested
     * 
     * @return 		a <code>String</code> containing at least the 
     *			servlet container name and version number
     *
     * @see ServletConfig#getInitParameter
     */
    public String getInitParameter(String name);


    /**
     * Returns the names of the context's initialization parameters as an
     * <code>Enumeration</code> of <code>String</code> objects, or an
     * empty <code>Enumeration</code> if the context has no initialization
     * parameters.
     *
     * @return 		an <code>Enumeration</code> of <code>String</code> 
     *                  objects containing the names of the context's
     *                  initialization parameters
     *
     * @see ServletConfig#getInitParameter
     */
    public Enumeration<String> getInitParameterNames();
    

    /**
     * Sets the context initialization parameter with the given name and
     * value on this ServletContext.
     *
     * 使用此ServletContext上的给定名称和值设置上下文初始化参数。
     *
     * @param name the name of the context initialization parameter to set
     * @param value the value of the context initialization parameter to set
     *
     * @return true if the context initialization parameter with the given
     * name and value was set successfully on this ServletContext, and false
     * if it was not set because this ServletContext already contains a
     * context initialization parameter with a matching name
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     * 
     * @since Servlet 3.0
     */
    public boolean setInitParameter(String name, String value);


    /**
     * Returns the servlet container attribute with the given name, 
     * or <code>null</code> if there is no attribute by that name.
     *
     * 返回具有给定名称的servlet容器属性，如果没有该名称的属性，则返回null。
     *
     * <p>An attribute allows a servlet container to give the
     * servlet additional information not
     * already provided by this interface. See your
     * server documentation for information about its attributes.
     * A list of supported attributes can be retrieved using
     * <code>getAttributeNames</code>.
     *
     * <p>The attribute is returned as a <code>java.lang.Object</code>
     * or some subclass.
     *
     * <p>Attribute names should follow the same convention as package
     * names. The Java Servlet API specification reserves names
     * matching <code>java.*</code>, <code>javax.*</code>,
     * and <code>sun.*</code>.
     *
     * @param name 	a <code>String</code> specifying the name 
     *			of the attribute
     *
     * @return 		an <code>Object</code> containing the value 
     *			of the attribute, or <code>null</code>
     *			if no attribute exists matching the given
     *			name
     *
     * @see 		ServletContext#getAttributeNames
     */
    public Object getAttribute(String name);
    

    /**
     * Returns an <code>Enumeration</code> containing the 
     * attribute names available within this ServletContext.
     *
     * 返回包含此ServletContext中可用的属性名的枚举。
     *
     * <p>Use the {@link #getAttribute} method with an attribute name
     * to get the value of an attribute.
     *
     * @return 		an <code>Enumeration</code> of attribute 
     *			names
     *
     * @see		#getAttribute
     */
    public Enumeration<String> getAttributeNames();
    
    
    /**
     * Binds an object to a given attribute name in this ServletContext. If
     * the name specified is already used for an attribute, this
     * method will replace the attribute with the new to the new attribute.
     * <p>If listeners are configured on the <code>ServletContext</code> the  
     * container notifies them accordingly.
     *
     * 将对象绑定到此ServletContext中的给定属性名称。
     * 如果指定的名称已经用于属性，则此方法将使用新属性中的新属性替换该属性。
     * 如果在ServletContext上配置了侦听器，则容器将相应地通知它们。
     *
     * <p>
     * If a null value is passed, the effect is the same as calling 
     * <code>removeAttribute()</code>.
     * 
     * <p>Attribute names should follow the same convention as package
     * names. The Java Servlet API specification reserves names
     * matching <code>java.*</code>, <code>javax.*</code>, and
     * <code>sun.*</code>.
     *
     * @param name 	a <code>String</code> specifying the name 
     *			of the attribute
     *
     * @param object 	an <code>Object</code> representing the
     *			attribute to be bound
     */
    public void setAttribute(String name, Object object);
    

    /**
     * Removes the attribute with the given name from 
     * this ServletContext. After removal, subsequent calls to
     * {@link #getAttribute} to retrieve the attribute's value
     * will return <code>null</code>.
     *
     * <p>If listeners are configured on the <code>ServletContext</code> the 
     * container notifies them accordingly.
     *
     * @param name	a <code>String</code> specifying the name 
     * 			of the attribute to be removed
     */
    public void removeAttribute(String name);

    
    /**
     * Returns the name of this web application corresponding to this
     * ServletContext as specified in the deployment descriptor for this
     * web application by the display-name element.
     *
     * 返回与此ServletContext对应的此web应用程序的名称，如display-name元素在该web应用程序的部署描述符中指定的。
     *
     * @return The name of the web application or null if no name has been
     * declared in the deployment descriptor.
     * 
     * @since Servlet 2.3
     */
    public String getServletContextName();


    /**
     * Adds the servlet with the given name and class name to this servlet
     * context.
     *
     * 将具有给定名称和类名的servlet添加到此servlet上下文。
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.
     *
     * <p>The specified <tt>className</tt> will be loaded using the 
     * classloader associated with the application represented by this
     * ServletContext.
     *
     * <p>If this ServletContext already contains a preliminary
     * ServletRegistration for a servlet with the given <tt>servletName</tt>,
     * it will be completed (by assigning the given <tt>className</tt> to it)
     * and returned.
     *
     * <p>This method introspects the class with the given <tt>className</tt>
     * for the {@link javax.servlet.annotation.ServletSecurity}, 
     * {@link javax.servlet.annotation.MultipartConfig},
     * <tt>javax.annotation.security.RunAs</tt>, and
     * <tt>javax.annotation.security.DeclareRoles</tt> annotations.
     * In addition, this method supports resource injection if the
     * class with the given <tt>className</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param servletName the name of the servlet
     * @param className the fully qualified class name of the servlet
     *
     * @return a ServletRegistration object that may be used to further
     * configure the registered servlet, or <tt>null</tt> if this
     * ServletContext already contains a complete ServletRegistration for
     * a servlet with the given <tt>servletName</tt> 
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws IllegalArgumentException if <code>servletName</code> is null
     * or an empty String
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(
            String servletName, String className);


    /**
     * Registers the given servlet instance with this ServletContext
     * under the given <tt>servletName</tt>.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * ServletRegistration for a servlet with the given <tt>servletName</tt>,
     * it will be completed (by assigning the class name of the given servlet
     * instance to it) and returned.
     *
     * @param servletName the name of the servlet
     * @param servlet the servlet instance to register
     *
     * @return a ServletRegistration object that may be used to further
     * configure the given servlet, or <tt>null</tt> if this
     * ServletContext already contains a complete ServletRegistration for a
     * servlet with the given <tt>servletName</tt> or if the same servlet
     * instance has already been registered with this or another
     * ServletContext in the same container
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @throws IllegalArgumentException if the given servlet instance 
     * implements {@link SingleThreadModel}, or <code>servletName</code> is null
     * or an empty String
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(
            String servletName, Servlet servlet);


    /**
     * Adds the servlet with the given name and class type to this servlet
     * context.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * ServletRegistration for a servlet with the given <tt>servletName</tt>,
     * it will be completed (by assigning the name of the given
     * <tt>servletClass</tt> to it) and returned.
     *
     * <p>This method introspects the given <tt>servletClass</tt> for
     * the {@link javax.servlet.annotation.ServletSecurity}, 
     * {@link javax.servlet.annotation.MultipartConfig},
     * <tt>javax.annotation.security.RunAs</tt>, and
     * <tt>javax.annotation.security.DeclareRoles</tt> annotations.
     * In addition, this method supports resource injection if the
     * given <tt>servletClass</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param servletName the name of the servlet
     * @param servletClass the class object from which the servlet will be
     * instantiated
     *
     * @return a ServletRegistration object that may be used to further
     * configure the registered servlet, or <tt>null</tt> if this
     * ServletContext already contains a complete ServletRegistration for
     * the given <tt>servletName</tt> 
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws IllegalArgumentException if <code>servletName</code> is null
     * or an empty String
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName,
                                                  Class<? extends Servlet> servletClass);


    /**
     * Instantiates the given Servlet class.
     *
     * <p>The returned Servlet instance may be further customized before it
     * is registered with this ServletContext via a call to 
     * {@link #addServlet(String,Servlet)}.
     *
     * <p>The given Servlet class must define a zero argument constructor,
     * which is used to instantiate it.
     *
     * <p>This method introspects the given <tt>clazz</tt> for
     * the following annotations:
     * {@link javax.servlet.annotation.ServletSecurity}, 
     * {@link javax.servlet.annotation.MultipartConfig},
     * <tt>javax.annotation.security.RunAs</tt>, and
     * <tt>javax.annotation.security.DeclareRoles</tt>.
     * In addition, this method supports resource injection if the
     * given <tt>clazz</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param clazz the Servlet class to instantiate
     *
     * @return the new Servlet instance
     *
     * @throws ServletException if the given <tt>clazz</tt> fails to be
     * instantiated
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public <T extends Servlet> T createServlet(Class<T> clazz)
        throws ServletException;

    /**
     * Gets the ServletRegistration corresponding to the servlet with the
     * given <tt>servletName</tt>.
     *
     * 获取与具有给定servletName的servlet对应的ServletRegistion。
     *
     * @return the (complete or preliminary) ServletRegistration for the
     * servlet with the given <tt>servletName</tt>, or null if no
     * ServletRegistration exists under that name
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public ServletRegistration getServletRegistration(String servletName);


    /**
     * Gets a (possibly empty) Map of the ServletRegistration
     * objects (keyed by servlet name) corresponding to all servlets
     * registered with this ServletContext.
     *
     * 获取与此ServletContext中注册的所有servlet对应的ServletRegisting对象（可能为空）映射（由servlet名称键入）
     *
     * <p>The returned Map includes the ServletRegistration objects
     * corresponding to all declared and annotated servlets, as well as the
     * ServletRegistration objects corresponding to all servlets that have
     * been added via one of the <tt>addServlet</tt> methods.
     *
     * <p>If permitted, any changes to the returned Map must not affect this
     * ServletContext.
     *
     * @return Map of the (complete and preliminary) ServletRegistration
     * objects corresponding to all servlets currently registered with this
     * ServletContext
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public Map<String, ? extends ServletRegistration> getServletRegistrations();


    /**
     * Adds the filter with the given name and class name to this servlet
     * context.
     *
     * 将具有给定名称和类名称的过滤器添加到此servlet上下文。
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.
     *
     * <p>The specified <tt>className</tt> will be loaded using the 
     * classloader associated with the application represented by this
     * ServletContext.
     *
     * <p>If this ServletContext already contains a preliminary
     * FilterRegistration for a filter with the given <tt>filterName</tt>,
     * it will be completed (by assigning the given <tt>className</tt> to it)
     * and returned.
     *
     * <p>This method supports resource injection if the class with the
     * given <tt>className</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param filterName the name of the filter
     * @param className the fully qualified class name of the filter
     *
     * @return a FilterRegistration object that may be used to further
     * configure the registered filter, or <tt>null</tt> if this
     * ServletContext already contains a complete FilterRegistration for
     * a filter with the given <tt>filterName</tt> 
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws IllegalArgumentException if <code>filterName</code> is null or
     * an empty String
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(
            String filterName, String className);
         

    /**
     * Registers the given filter instance with this ServletContext
     * under the given <tt>filterName</tt>.
     *
     * 在给定的filterName下用此ServletContext注册给定的筛选器实例。
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * FilterRegistration for a filter with the given <tt>filterName</tt>,
     * it will be completed (by assigning the class name of the given filter
     * instance to it) and returned.
     *
     * @param filterName the name of the filter
     * @param filter the filter instance to register
     *
     * @return a FilterRegistration object that may be used to further
     * configure the given filter, or <tt>null</tt> if this
     * ServletContext already contains a complete FilterRegistration for a
     * filter with the given <tt>filterName</tt> or if the same filter
     * instance has already been registered with this or another
     * ServletContext in the same container
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws IllegalArgumentException if <code>filterName</code> is null or
     * an empty String
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(
            String filterName, Filter filter);


    /**
     * Adds the filter with the given name and class type to this servlet
     * context.
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * FilterRegistration for a filter with the given <tt>filterName</tt>,
     * it will be completed (by assigning the name of the given
     * <tt>filterClass</tt> to it) and returned.
     *
     * <p>This method supports resource injection if the given
     * <tt>filterClass</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param filterName the name of the filter
     * @param filterClass the class object from which the filter will be
     * instantiated
     *
     * @return a FilterRegistration object that may be used to further
     * configure the registered filter, or <tt>null</tt> if this
     * ServletContext already contains a complete FilterRegistration for a
     * filter with the given <tt>filterName</tt> 
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws IllegalArgumentException if <code>filterName</code> is null or
     * an empty String
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName,
                                                Class<? extends Filter> filterClass);


    /**
     * Instantiates the given Filter class.
     *
     * <p>The returned Filter instance may be further customized before it
     * is registered with this ServletContext via a call to 
     * {@link #addFilter(String,Filter)}.
     *
     * <p>The given Filter class must define a zero argument constructor,
     * which is used to instantiate it.
     *
     * <p>This method supports resource injection if the given
     * <tt>clazz</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param clazz the Filter class to instantiate
     *
     * @return the new Filter instance
     *
     * @throws ServletException if the given <tt>clazz</tt> fails to be
     * instantiated
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public <T extends Filter> T createFilter(Class<T> clazz)
        throws ServletException;


    /**
     * Gets the FilterRegistration corresponding to the filter with the
     * given <tt>filterName</tt>.
     *
     * 获取与具有给定filterName的过滤器对应的FilterRegistion。
     *
     * @return the (complete or preliminary) FilterRegistration for the
     * filter with the given <tt>filterName</tt>, or null if no
     * FilterRegistration exists under that name
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public FilterRegistration getFilterRegistration(String filterName);


    /**
     * Gets a (possibly empty) Map of the FilterRegistration
     * objects (keyed by filter name) corresponding to all filters
     * registered with this ServletContext.
     *
     * 获取与此ServletContext中注册的所有过滤器相对应的FilterRegistration对象（可能为空）映射（由过滤器名称键入）。
     *
     * <p>The returned Map includes the FilterRegistration objects
     * corresponding to all declared and annotated filters, as well as the
     * FilterRegistration objects corresponding to all filters that have
     * been added via one of the <tt>addFilter</tt> methods.
     *
     * <p>Any changes to the returned Map must not affect this
     * ServletContext.
     *
     * @return Map of the (complete and preliminary) FilterRegistration
     * objects corresponding to all filters currently registered with this
     * ServletContext
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public Map<String, ? extends FilterRegistration> getFilterRegistrations();


    /**
     * Gets the {@link SessionCookieConfig} object through which various
     * properties of the session tracking cookies created on behalf of this
     * <tt>ServletContext</tt> may be configured.
     *
     * 获取{@link SessionCookieConfig}对象，通过该对象可以配置代表此ServletContext创建的会话跟踪cookie的各种属性。
     *
     * <p>Repeated invocations of this method will return the same
     * <tt>SessionCookieConfig</tt> instance.
     *
     * @return the <tt>SessionCookieConfig</tt> object through which
     * various properties of the session tracking cookies created on
     * behalf of this <tt>ServletContext</tt> may be configured
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public SessionCookieConfig getSessionCookieConfig();


    /**
     * Sets the session tracking modes that are to become effective for this
     * <tt>ServletContext</tt>.
     *
     * 设置将对此ServletContext有效的会话跟踪模式。
     *
     * <p>The given <tt>sessionTrackingModes</tt> replaces any
     * session tracking modes set by a previous invocation of this
     * method on this <tt>ServletContext</tt>.
     *
     * @param sessionTrackingModes the set of session tracking modes to
     * become effective for this <tt>ServletContext</tt>
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @throws IllegalArgumentException if <tt>sessionTrackingModes</tt>
     * specifies a combination of <tt>SessionTrackingMode.SSL</tt> with a
     * session tracking mode other than <tt>SessionTrackingMode.SSL</tt>,
     * or if <tt>sessionTrackingModes</tt> specifies a session tracking mode
     * that is not supported by the servlet container
     *
     * @since Servlet 3.0
     */
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes);


    /**
     * Gets the session tracking modes that are supported by default for this
     * <tt>ServletContext</tt>.
     *
     * 获取默认情况下此ServletContext支持的会话跟踪模式。
     *
     * @return set of the session tracking modes supported by default for
     * this <tt>ServletContext</tt>
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes();


    /**
     * Gets the session tracking modes that are in effect for this
     * <tt>ServletContext</tt>.
     *
     * 获取对该ServletContext有效的会话跟踪模式。
     *
     * <p>The session tracking modes in effect are those provided to
     * {@link #setSessionTrackingModes setSessionTrackingModes}.
     *
     * <p>By default, the session tracking modes returned by
     * {@link #getDefaultSessionTrackingModes getDefaultSessionTrackingModes}
     * are in effect.
     *
     * @return set of the session tracking modes in effect for this
     * <tt>ServletContext</tt>
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes();


    /**
     * Adds the listener with the given class name to this ServletContext.
     *
     * 将具有给定类名的侦听器添加到此ServletContext。
     *
     * <p>The class with the given name will be loaded using the
     * classloader associated with the application represented by this
     * ServletContext, and must implement one or more of the following
     * interfaces:
     * <ul>
     * <li>{@link ServletContextAttributeListener}</tt>
     * <li>{@link ServletRequestListener}</tt>
     * <li>{@link ServletRequestAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionIdListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionListener}</tt>
     * </ul>
     *
     * <p>If this ServletContext was passed to 
     * {@link ServletContainerInitializer#onStartup}, then the class with
     * the given name may also implement {@link ServletContextListener},
     * in addition to the interfaces listed above.
     *
     * <p>As part of this method call, the container must load the class
     * with the specified class name to ensure that it implements one of 
     * the required interfaces.
     *
     * <p>If the class with the given name implements a listener interface
     * whose invocation order corresponds to the declaration order (i.e.,
     * if it implements {@link ServletRequestListener},
     * {@link ServletContextListener}, or
     * {@link javax.servlet.http.HttpSessionListener}),
     * then the new listener will be added to the end of the ordered list of
     * listeners of that interface.
     *
     * <p>This method supports resource injection if the class with the
     * given <tt>className</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param className the fully qualified class name of the listener
     *
     * @throws IllegalArgumentException if the class with the given name
     * does not implement any of the above interfaces, or if it implements
     * {@link ServletContextListener} and this ServletContext was not
     * passed to {@link ServletContainerInitializer#onStartup}
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public void addListener(String className);


    /**
     * Adds the given listener to this ServletContext.
     *
     * 将给定的侦听器添加到此ServletContext。
     *
     * <p>The given listener must be an instance of one or more of the
     * following interfaces:
     * <ul>
     * <li>{@link ServletContextAttributeListener}</tt>
     * <li>{@link ServletRequestListener}</tt>
     * <li>{@link ServletRequestAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionIdListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionListener}</tt>
     * </ul>
     *
     * <p>If this ServletContext was passed to 
     * {@link ServletContainerInitializer#onStartup}, then the given
     * listener may also be an instance of {@link ServletContextListener},
     * in addition to the interfaces listed above.
     *
     * <p>If the given listener is an instance of a listener interface whose
     * invocation order corresponds to the declaration order (i.e., if it
     * is an instance of {@link ServletRequestListener},
     * {@link ServletContextListener}, or
     * {@link javax.servlet.http.HttpSessionListener}),
     * then the listener will be added to the end of the ordered list of
     * listeners of that interface.
     *
     * @param t the listener to be added
     *
     * @throws IllegalArgumentException if the given listener is not
     * an instance of any of the above interfaces, or if it is an instance
     * of {@link ServletContextListener} and this ServletContext was not
     * passed to {@link ServletContainerInitializer#onStartup}
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public <T extends EventListener> void addListener(T t);


    /**
     * Adds a listener of the given class type to this ServletContext.
     *
     * <p>The given <tt>listenerClass</tt> must implement one or more of the
     * following interfaces:
     * <ul>
     * <li>{@link ServletContextAttributeListener}</tt>
     * <li>{@link ServletRequestListener}</tt>
     * <li>{@link ServletRequestAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionIdListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionListener}</tt>
     * </ul>
     *
     * <p>If this ServletContext was passed to 
     * {@link ServletContainerInitializer#onStartup}, then the given
     * <tt>listenerClass</tt> may also implement
     * {@link ServletContextListener}, in addition to the interfaces listed
     * above.
     *
     * <p>If the given <tt>listenerClass</tt> implements a listener
     * interface whose invocation order corresponds to the declaration order
     * (i.e., if it implements {@link ServletRequestListener},
     * {@link ServletContextListener}, or
     * {@link javax.servlet.http.HttpSessionListener}),
     * then the new listener will be added to the end of the ordered list
     * of listeners of that interface.
     *
     * <p>This method supports resource injection if the given
     * <tt>listenerClass</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param listenerClass the listener class to be instantiated
     *
     * @throws IllegalArgumentException if the given <tt>listenerClass</tt>
     * does not implement any of the above interfaces, or if it implements
     * {@link ServletContextListener} and this ServletContext was not passed
     * to {@link ServletContainerInitializer#onStartup}
     *
     * @throws IllegalStateException if this ServletContext has already
     * been initialized
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.0
     */
    public void addListener(Class<? extends EventListener> listenerClass);


    /**
     * Instantiates the given EventListener class.
     *
     * <p>The specified EventListener class must implement at least one of
     * the <code>{@link ServletContextListener}</code>,
     * <code>{@link ServletContextAttributeListener}</code>,
     * <code>{@link ServletRequestListener}</code>,
     * <code>{@link ServletRequestAttributeListener}</code>,
     * <code>{@link javax.servlet.http.HttpSessionAttributeListener}</code>
     * <code>{@link javax.servlet.http.HttpSessionIdListener}</code>, or
     * <code>{@link javax.servlet.http.HttpSessionListener}</code>, or
     * interfaces.
     *
     * <p>The returned EventListener instance may be further customized
     * before it is registered with this ServletContext via a call to
     * {@link #addListener(EventListener)}.
     *
     * <p>The given EventListener class must define a zero argument
     * constructor, which is used to instantiate it.
     *
     * <p>This method supports resource injection if the given
     * <tt>clazz</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param clazz the EventListener class to instantiate
     *
     * @return the new EventListener instance
     *
     * @throws ServletException if the given <tt>clazz</tt> fails to be
     * instantiated
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @throws IllegalArgumentException if the specified EventListener class
     * does not implement any of the
     * <code>{@link ServletContextListener}</code>,
     * <code>{@link ServletContextAttributeListener}</code>,
     * <code>{@link ServletRequestListener}</code>,
     * <code>{@link ServletRequestAttributeListener}</code>,
     * <code>{@link javax.servlet.http.HttpSessionAttributeListener}</code>
     * <code>{@link javax.servlet.http.HttpSessionIdListener}</code>, or
     * <code>{@link javax.servlet.http.HttpSessionListener}</code>, or
     * interfaces.
     *
     * @since Servlet 3.0
     */
    public <T extends EventListener> T createListener(Class<T> clazz)
        throws ServletException; 


    /**
     * Gets the <code>&lt;jsp-config&gt;</code> related configuration
     * that was aggregated from the <code>web.xml</code> and
     * <code>web-fragment.xml</code> descriptor files of the web application
     * represented by this ServletContext.
     *
     * 获取从此ServletContext表示的web应用程序的web.xml和web-fra..xml描述符文件中聚合的jsp-config相关配置。
     *
     *
     * @return the <code>&lt;jsp-config&gt;</code> related configuration
     * that was aggregated from the <code>web.xml</code> and
     * <code>web-fragment.xml</code> descriptor files of the web application
     * represented by this ServletContext, or null if no such configuration
     * exists
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @see JspConfigDescriptor
     *
     * @since Servlet 3.0
     */
    public JspConfigDescriptor getJspConfigDescriptor();


    /**
     * Gets the class loader of the web application represented by this
     * ServletContext.
     *
     * 获取此ServletContext标识的web应用程序的类加载器
     *
     * <p>If a security manager exists, and the caller's class loader
     * is not the same as, or an ancestor of the requested class loader,
     * then the security manager's <code>checkPermission</code> method is
     * called with a <code>RuntimePermission("getClassLoader")</code>
     * permission to check whether access to the requested class loader
     * should be granted.
     *
     * @return the class loader of the web application represented by this
     * ServletContext
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @throws SecurityException if a security manager denies access to 
     * the requested class loader
     *
     * @since Servlet 3.0
     */
    public ClassLoader getClassLoader();


    /**
     * Declares role names that are tested using <code>isUserInRole</code>.
     *
     * 声明使用isUserRole测试的角色名称
     *
     * <p>Roles that are implicitly declared as a result of their use within
     * the {@link ServletRegistration.Dynamic#setServletSecurity
     * setServletSecurity} or {@link ServletRegistration.Dynamic#setRunAsRole
     * setRunAsRole} methods of the {@link ServletRegistration} interface need
     * not be declared.
     *
     * @param roleNames the role names being declared
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @throws IllegalArgumentException if any of the argument roleNames is
     * null or the empty string
     *
     * @throws IllegalStateException if the ServletContext has already
     * been initialized
     *
     * @since Servlet 3.0
     */
    public void declareRoles(String... roleNames);


    /**
     * Returns the configuration name of the logical host on which the
     * ServletContext is deployed.
     *
     * 返回部署ServletContext的逻辑主机的配置名。
     *
     * Servlet containers may support multiple logical hosts. This method must
     * return the same name for all the servlet contexts deployed on a logical
     * host, and the name returned by this method must be distinct, stable per
     * logical host, and suitable for use in associating server configuration
     * information with the logical host. The returned value is NOT expected
     * or required to be equivalent to a network address or hostname of the
     * logical host.
     *
     * @return a <code>String</code> containing the configuration name of the
     * logical host on which the servlet context is deployed.
     *
     * @throws UnsupportedOperationException if this ServletContext was
     * passed to the {@link ServletContextListener#contextInitialized} method
     * of a {@link ServletContextListener} that was neither declared in
     * <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     * with {@link javax.servlet.annotation.WebListener}
     *
     * @since Servlet 3.1
     */
    public String getVirtualServerName();
}
