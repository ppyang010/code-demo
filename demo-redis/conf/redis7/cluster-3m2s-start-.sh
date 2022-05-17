redis-server ./redis-cluster-5100.conf
redis-server ./redis-cluster-5101.conf
redis-server ./redis-cluster-5102.conf
redis-server ./redis-cluster-5200.conf
redis-server ./redis-cluster-5201.conf
redis-server ./redis-cluster-5202.conf
redis-server ./redis-cluster-5300.conf
redis-server ./redis-cluster-5301.conf
redis-server ./redis-cluster-5302.conf
# 注意清楚rdb
# --cluster-replicas 1 意味着为每个主节点都提供一个从节点
redis-cli --cluster create 127.0.0.1:5100 127.0.0.1:5200 127.0.0.1:5300 127.0.0.1:5101 127.0.0.1:5102 127.0.0.1:5201 127.0.0.1:5202 127.0.0.1:5301 127.0.0.1:5302 --cluster-replicas 2