/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;


/**
 *
 * Servlet：小服务程序
 *
 * Defines methods that all servlets must implement.
 *
 * <p>A servlet is a small Java program that runs within a Web server.
 * Servlets receive and respond to requests from Web clients,
 * usually across HTTP, the HyperText Transfer Protocol. 
 *
 * Servlet是一个在WEB服务器中运行的小型Java程序
 * Servlet通常通过HTTP(超文本传输协议)接收并响应来自WEB客户端的请求
 *
 * <p>To implement this interface, you can write a generic servlet
 * that extends
 *
 * 可以通过编写一个通用的servelt实现该接口来进行拓展
 *
 * <code>javax.servlet.GenericServlet</code> or an HTTP servlet that
 * extends <code>javax.servlet.http.HttpServlet</code>.
 *
 * <p>This interface defines methods to initialize a servlet,
 * to service requests, and to remove a servlet from the server.
 *
 * 该接口定义了初始化Servlet、服务请求、从服务中移除Servlet的方法
 *
 * These are known as life-cycle methods and are called in the
 * following sequence:
 * <ol>
 * <li>The servlet is constructed, then initialized with the <code>init</code> method.
 *
 * 构建Servlet，使用init方法初始化
 *
 * <li>Any calls from clients to the <code>service</code> method are handled.
 *
 *  客户端对service方法的调用都将被处理
 *
 * <li>The servlet is taken out of service, then destroyed with the 
 * <code>destroy</code> method, then garbage collected and finalized.
 *
 * Servlet退出服务，使用destroy方法销毁，然后垃圾回收器回收并完成
 *
 * </ol>
 *
 * <p>In addition to the life-cycle methods, this interface
 * provides the <code>getServletConfig</code> method, which the servlet 
 * can use to get any startup information, and the <code>getServletInfo</code>
 * method, which allows the servlet to return basic information about itself,
 * such as author, version, and copyright.
 *
 * 除了生命周期方法外，该接口提供getServletConfig方法。使用该方法Servlet可以获取任何启动信息。
 * 而getServletInfo方法，他允许返回其自身的皆不能信息如作者、版本和版权。
 *
 * @author 	Various
 *
 * @see 	GenericServlet
 * @see 	javax.servlet.http.HttpServlet
 *
 */


public interface Servlet {

    /**
     * Called by the servlet container to indicate to a servlet that the 
     * servlet is being placed into service.
     *
     * 由Servlet容器调用以向Servlet指示Servlet正在被放置到服务中
     *
     * <p>The servlet container calls the <code>init</code>
     * method exactly once after instantiating the servlet.
     * The <code>init</code> method must complete successfully
     * before the servlet can receive any requests.
     *
     * Servlet容器在实例化Servlet后调用init方法一次。
     * init方法必须在Servlet接收请求前完成初始化
     *
     * <p>The servlet container cannot place the servlet into service
     * if the <code>init</code> method
     * <ol>
     * <li>Throws a <code>ServletException</code>
     *
     * 抛出ServleteException异常
     *
     * <li>Does not return within a time period defined by the Web server
     *
     * 不在WEB服务器定义的时间段内返回
     *
     * </ol>
     *
     *
     * @param config	a <code>ServletConfig</code> object
     *					containing the servlet's
     * 					configuration and initialization parameters
     *
     * ServletConfig对象，包含servelt的配置和初始化参数
     *
     * @exception ServletException 	if an exception has occurred that
     *					interferes with the servlet's normal
     *					operation
     *
     * 当发生干扰Servlet正常操作的异常
     *
     * @see 				UnavailableException
     * @see 				#getServletConfig
     *
     */

    public void init(ServletConfig config) throws ServletException;
    
    

    /**
     *
     * Returns a {@link ServletConfig} object, which contains
     * initialization and startup parameters for this servlet.
     *
     * 返回一个ServletConfig对象，包含Servlet初始化信息和启动参数
     *
     * The <code>ServletConfig</code> object returned is the one 
     * passed to the <code>init</code> method.
     *
     * 返回的ServletConfig对象是传递给init方法的对象
     *
     * <p>Implementations of this interface are responsible for storing the 
     * <code>ServletConfig</code> object so that this 
     * method can return it. The {@link GenericServlet}
     * class, which implements this interface, already does this.
     *
     * 该接口的实现负责存储ServletConfig对象，以便该方法返回对象。
     * 该接口的实现GenericServlet方法，已经实现存储ServletConfig对象。
     *
     * @return		the <code>ServletConfig</code> object
     *			that initializes this servlet
     *
     * @see 		#init
     *
     */

    public ServletConfig getServletConfig();
    
    

    /**
     * Called by the servlet container to allow the servlet to respond to 
     * a request.
     *
     * Servlet容器调用以相应请求
     *
     * <p>This method is only called after the servlet's <code>init()</code>
     * method has completed successfully.
     *
     * 该方法仅在Servlet的init方法成功调用后调用
     *
     * <p>  The status code of the response always should be set for a servlet 
     * that throws or sends an error.
     *
     * Servelt抛出或发送失败，响应的状态代码将被设置
     *
     * 
     * <p>Servlets typically run inside multithreaded servlet containers
     * that can handle multiple requests concurrently. Developers must 
     * be aware to synchronize access to any shared resources such as files,
     * network connections, and as well as the servlet's class and instance 
     * variables.
     *
     * Servlet通常在可以同时处理多个请求的多线程Servlet容器中运行。开发人员必须知道如何同步对任何共享资源的访问，
     * 如文件、网络连接以及Servlet的类和实例变量。
     *
     * More information on multithreaded programming in Java is available in 
     * <a href="http://java.sun.com/Series/Tutorial/java/threads/multithreaded.html">
     * the Java tutorial on multi-threaded programming</a>.
     *
     *
     * @param req 	the <code>ServletRequest</code> object that contains
     *			the client's request
     *
     * @param res 	the <code>ServletResponse</code> object that contains
     *			the servlet's response
     *
     * @exception ServletException 	if an exception occurs that interferes
     *					with the servlet's normal operation 
     *
     * @exception IOException 		if an input or output exception occurs
     *
     */

    public void service(ServletRequest req, ServletResponse res)
	throws ServletException, IOException;
	
	

    /**
     * Returns information about the servlet, such
     * as author, version, and copyright.
     *
     * 返回Servlet的信息，如作者、版本和版权
     *
     * <p>The string that this method returns should
     * be plain text and not markup of any kind (such as HTML, XML,
     * etc.).
     *
     * 此方法返回的字符串应该是纯文本，而不是任何类型的标记
     *
     * @return 		a <code>String</code> containing servlet information
     *
     */

    public String getServletInfo();
    
    

    /**
     *
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being taken out of service.  This method is
     * only called once all threads within the servlet's
     * <code>service</code> method have exited or after a timeout
     * period has passed. After the servlet container calls this 
     * method, it will not call the <code>service</code> method again
     * on this servlet.
     *
     * 由Servlet容器调用以向Servlet指示Servlet正在退出服务。
     * 此方法只在Servlet的{service}方法中的所有线程推相互或超时时间段过过去之后调用。
     * 在Servlet容器调用此方法之后，他不会在这个Servlet上再次调用{service}方法
     *
     * <p>This method gives the servlet an opportunity 
     * to clean up any resources that are being held (for example, memory,
     * file handles, threads) and make sure that any persistent state is
     * synchronized with the servlet's current state in memory.
     *
     * 此方法为Servlet提供了清理所持有的任何资源（例如，内存、文件句柄、线程）
     * 并确保任何持久状态与servlet在内存中的当前状态同步的机会
     *
     */

    public void destroy();
}
