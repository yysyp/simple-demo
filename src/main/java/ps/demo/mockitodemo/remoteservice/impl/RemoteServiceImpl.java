package ps.demo.mockitodemo.remoteservice.impl;

import org.springframework.util.StringUtils;

import ps.demo.mockitodemo.exception.MockException;
import ps.demo.mockitodemo.model.Node;
import ps.demo.mockitodemo.remoteservice.IRemoteService;

public class RemoteServiceImpl implements IRemoteService {

    @Override
    public Node getRemoteNode(int num) {
        return new Node(num, "Node from remote service");
    }

    @Override
    public final Node getFinalNode() {
        return new Node(1, "final node");
    }

    @Override
    public Node getRemoteNode(String name) throws MockException {
        if (StringUtils.isEmpty(name)) {
            throw new MockException("name不能为空", name);
        }
        return new Node(name);
    }

    @Override
    public void doSometing() {
        System.out.println("remote service do something!");
    }

    @Override
    public Node getPrivateNode() {
        return privateMethod();
    }

    private Node privateMethod() {
        return new Node(1, "private node");
    }

    @Override
    public Node getSystemPropertyNode() {
        return new Node(System.getProperty("abc"));
    }
}