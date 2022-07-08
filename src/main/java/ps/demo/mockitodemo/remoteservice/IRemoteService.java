package ps.demo.mockitodemo.remoteservice;

import ps.demo.mockitodemo.exception.MockException;
import ps.demo.mockitodemo.model.Node;

public interface IRemoteService {
    public Node getRemoteNode(int num);

    public Node getRemoteNode(String name) throws MockException;

    public void doSometing();

    public Node getFinalNode();

    public Node getPrivateNode();

    public Node getSystemPropertyNode();
}
