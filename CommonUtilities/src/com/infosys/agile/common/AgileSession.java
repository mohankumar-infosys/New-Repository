/**
 * 
 */
package com.infosys.agile.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;

/**
 * @author Ranjan.Nayak This class captures all the wrapper utility methods implemented for creating
 *         and closing Agile Session object
 */

public class AgileSession
{

    /**
     * Create a session for the Agile PLM server instance
     * 
     * @param String UserName to create session
     * @param String Password to create session
     * @param String Agile Application URL
     * @return IAgileSession Agile Session
     * @throws APIException APIException
     */
    public IAgileSession createAgileSession(final String userName,
                                            final String password,
                                            final String agileURL)
                                                throws APIException
    {
        final AgileSessionFactory factory = AgileSessionFactory.getInstance(agileURL);
        final Map<Integer, String> params = new HashMap<Integer, String>();
        params.put(AgileSessionFactory.USERNAME, userName);
        params.put(AgileSessionFactory.PASSWORD, password);
        return factory.createSession(params);
    }


    /**
     * Creating a session by accessing a password-controlled URL
     * 
     * @param String UserName to create session
     * @param String Password to create session
     * @param String Agile Application URL
     * @return IAgileSession Agile Session
     * @throws APIException APIException
     */
    public IAgileSession createAgileSessionForPasswordControlledURL(final String userName,
                                                                    final String password,
                                                                    final String agileURL)
                                                                        throws APIException
    {
        final Map<Integer, String> params = new HashMap<Integer, String>();
        params.put(AgileSessionFactory.URL, agileURL);
        params.put(AgileSessionFactory.USERNAME, userName);
        params.put(AgileSessionFactory.PASSWORD, password);
        return AgileSessionFactory.createSessionEx(params);
    }


    /**
     * Creating a session by accessing the HTTP request object
     * 
     * @param String Agile Application URL
     * @param HttpServletRequest HTTP Request object
     * @return IAgileSession Agile Session
     * @throws APIException APIException
     */
    public IAgileSession createAgileSessionUsingHttpRequest(final String agileURL,
                                                            final HttpServletRequest request)
                                                                throws APIException
    {
        final Map<Integer, Object> params = new HashMap<Integer, Object>();
        final AgileSessionFactory factory = AgileSessionFactory.getInstance(agileURL);
        params.put(AgileSessionFactory.PX_REQUEST, request);
        return factory.createSession(params);
    }


    /**
     * Creating a session by accessing the cookies
     * 
     * @param String Agile Application URL
     * @param Cookie[] Http Cookies
     * @return IAgileSession Agile Session
     * @throws APIException APIException
     */
    public IAgileSession createAgileSessionUsingCookies(final String agileURL,
                                                        final Cookie[] cookies)
                                                            throws APIException
    {
        final AgileSessionFactory factory = AgileSessionFactory.getInstance(agileURL);
        final Map<Integer, Object> params = new HashMap<Integer, Object>();
        String username = "";
        String password = "";
        // Get the username and password from the cookies
        for (int i = 0; i < cookies.length; i++)
        {
            if (cookies[i].getName().equals("j_username"))
            {
                username = cookies[i].getValue();
            }
            else if (cookies[i].getName().equals("j_password"))
            {
                password = cookies[i].getValue();
            }

        }
        params.put(AgileSessionFactory.PX_USERNAME, username);
        params.put(AgileSessionFactory.PX_PASSWORD, password);
        return factory.createSession(params);
    }


    /**
     * Close the agile session.
     * 
     * @param IAgileSession Agile Session
     * @return boolean
     * @throws APIException APIException
     */
    public boolean closeAgileSession(final IAgileSession session)
        throws APIException
    {
        session.close();
        return true;
    }

}
