package ps.demo.mockitodemo.localservice;

import ps.demo.mockitodemo.exception.MockException;
import ps.demo.mockitodemo.model.Node;

public interface ILocalService {

    public Node getRemoteNode(int num);

    public Node getRemoteNode(String name) throws MockException;

    public void remoteDoSomething();

    public Node getLocalNode(int num, String name);
}
