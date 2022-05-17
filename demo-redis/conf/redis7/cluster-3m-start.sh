redis-server ./redis-cluster-5100.conf
redis-server ./redis-cluster-5200.conf
redis-server ./redis-cluster-5300.conf

# 注意清楚rdb
# --cluster-replicas 1 意味着为每个主节点都提供一个从节点
redis-cli --cluster create 127.0.0.1:5100 127.0.0.1:5200 127.0.0.1:5300 