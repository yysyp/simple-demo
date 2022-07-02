kubectl delete ns app
kubectl create ns app
kubectl apply -f nginx-pod.yaml
kubectl wait -n app --for=condition=ready pod -l app=ngx --timeout=120s
kubectl get po -n app
curl -kv http://localhost:31888/
