package ps.demo.mockitodemo.localservice.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ps.demo.mockitodemo.exception.MockException;
import ps.demo.mockitodemo.localservice.ILocalService;
import ps.demo.mockitodemo.model.Node;
import ps.demo.mockitodemo.remoteservice.IRemoteService;

public class LocalServiceImpl implements ILocalService {

    @Autowired
    private IRemoteService remoteService;

    @Override
    public Node getLocalNode(int num, String name) {
        return new Node(num, name);
    }

    @Override
    public Node getRemoteNode(int num) {
        return remoteService.getRemoteNode(num);
    }

    @Override
    public Node getRemoteNode(String name) throws MockException {
        try {
            return remoteService.getRemoteNode(name);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public void remoteDoSomething() {
        remoteService.doSometing();
    }
}
