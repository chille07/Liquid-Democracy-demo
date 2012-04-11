/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iaik.chille.ballotsigner;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author chille
 */
@WebService(serviceName = "BallotSigner")
public class BallotSigner {
  String id = null;

  /**
   * Web service operation
   */
  @WebMethod(operationName = "login")
  public String login(@WebParam(name = "id") String id, @WebParam(name = "something") String something)
  {
    if(something.compareTo("xyz")==0) // TODO: dummy login check
      // check via handysignature or citizen card
    {
      this.id = id;
      return "OK";
    }
    else
    {
      this.id = null;
      return "Login Failed. Use any ID and 'xyz' for something";
    }
  }

  /**
   * Web service operation
   * vote should be an xml code
   */
  @WebMethod(operationName = "getVoteSigned")
  public String getVoteSigned(@WebParam(name = "vote") String vote)
  {
    if(id != null)
    {
      // TODO: check the vote if visible information is valid and if the user has not voted yet for the topic.
      

      // TODO: do a real digital signature
      return "["+vote+"]"; // signed
    }
    else
    {
      return "Error: not logged in";
    }
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getVoteRejectionList")
  public String getVoteRejectionList(@WebParam(name = "electionid") String electionid)
  {
    //TODO write your implementation code here:
    return null;
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "reject")
  public String reject(@WebParam(name = "rejection_vote") String rejection_vote)
  {
    //TODO write your implementation code here:
    return null;
  }
}
