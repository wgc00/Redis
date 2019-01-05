## Redis 命令（不区分大小写）
### NoSQL
MongoDB（是js语法、文档）   
Redis（K-V数据库）   
共同特点：会占用大量的内存、把数据放到内存，

###  一、Key(键)
  1、del           &emsp; &emsp;                                                                   删除    
  2、dump                  &emsp;                                                       序列化   
  3、restore key（给一个名字保存） ttl（秒） value（被序列化的值）              反序列化     
  4、exists        &emsp;    判断这个key是否存在   
  5、expire        &emsp;    设置这个key生存时间（秒为单位）  
  6、expireat      &emsp;    设置key的过期时间（）     
  7、keys          &emsp;    *表示查询所有，?一个问号表示一字母，占位的，
  如果db, keys ??就查询出db，如果是一个?就不能查询db出来   
  8、migrate        &emsp;   将 key 原子性地从当前实例传送到目标实例的指定数据库上，如果成功传过去的key会被删除  
  9、move           &emsp;  迁移到其他号数据库，如果失败返回0  
  10、object        &emsp;  当将Redis用作缓存程序时，决定 key 的驱逐策略；三个值可以引用refcount|encoding|idletime  
  11、persist       &emsp;  设置生存的时间（秒单位），时间过了返回-1  
  12、pexpire       &emsp;  设置生存的时间，（秒单位）  
  13、pexpireat     &emsp; 设置生存时间（毫秒为单位）  
  14、pttl          &emsp; 查询当前的毫秒  
  15、randomkey     &emsp; 随机读取一个key      
  16、rename        &emsp; 修改key的名字  
  17、renamenx      &emsp; 如果key存在，修改失败返回0，不存在修改成功，返回1  
  18、restore       &emsp;   序列化  
  19、sort          &emsp; 排序，如果sort key desc 反序  
  20、ttl           &emsp; 查询时间秒  
  21、type          &emsp; 查询类型  
  22、scan          &emsp; 
### 二、String（字符串）
  
  1、 appEnd       &emsp; 在字符串的后面追加上新的内容    
  2、 bitCount  
  3、 bitOp       
  4、 bitField  
  5、 decr         &emsp; 自身减1  
  6、 decrby       &emsp; 通过输入的数（值 = 原有总数 - 输入数 ）    
  7、 get          &emsp; 通过key查找值    
  8、 getBit  
  9、 getRange     &emsp; 字符串截取（可以使用负数截取）  
  10、getSet       &emsp; 给新的值，返回一个旧的值，如果没有旧的值返回nil（get查询就能得到新的值）  
  11、incr         &emsp; 存储的数字自身加1  
  12、incrby       &emsp; 通过输入的数（值 = 原有总数 + 输入数 ）  
  13、incrByFloat  &emsp; 浮点数（小数）相增加  
  14、mGet         &emsp; 查询多个值（key key）如果没有返回nil  
  15、mSet         &emsp; 同时设置多个值（key  value key value）  
  16、mSetNx       &emsp; 同时设置多个值，如果这个key存在，那么设置的值将失败  
  17、pSetEx       &emsp; 设置的值一毫秒为生存时间，如果这个时间到了，这个key就会消亡  
  18、set          &emsp; 设置一个值  
  19、setBit
  20、setEx       &emsp;  设置这个值生存的时间（秒）  
  21、setNx       &emsp;  这个设置值不会被setNx再次设置时覆盖，如果key已有值，就会返回0  
  22、setRange    &emsp;  插入数据（key index 要插入的值）  
  23、strLen      &emsp;  返回一个key的长度   
 
  
 ### 三、Hash
   
   1、 hset           &emsp;设置一个值或多个值  
   2、 hget           &emsp;获取一个值   
   3、 hgetall        &emsp;获取全部的值   
   4、 hmset          &emsp;设置一个名字多个属性名和值  
   5、 hmget          &emsp;可以获取多个值   
   6、 hdel           &emsp;删除多个或一个   
   7、 hexists        &emsp;判断这个元素中是否存在，使用field作为条件  
   8、 hincrby        &emsp;可以加减，+数值， -数值  
   9、 hincrbyfloat   &emsp;可以加减，+小数， -小数  
   10、hkeys          &emsp;返回一个key所在的field  
   11、hlen           &emsp;获取长度  
   12、hsetnx         &emsp;key不予许重复  
   13、hvals          &emsp;返回key中的值  
   14、hscan          &emsp;迭代器（数据多时，一般我们不适用keys查询）  
   15、hstrlen        &emsp;key的长度  
   
   ### 四、List
   lpush       &emsp;  保存，（如果是使用lpop弹出的话，是先进后出）比如：插入1、2、3 查询是3、2、1、   
   lpop        &emsp;  弹出一个元素，取出一个元素，但这个元素也会被删除（弹出最后插入的数）    
   lrange      &emsp;  查看集合中的值    
   llen        &emsp; 获取长度    
   rpush       &emsp;  保存，插入是：1、2、3  查询是：1、2、3   
   rpop        &emsp;  弹出一个元素，取出一个元素，但这个元素也会被删除（弹出最先插入的数）   
   ### 五、Set
   1、sadd  		&emsp;添加重复的元素、但值不能一样  
   2、smembers &emsp;	查看sadd添加的元素  
   3、scard		&emsp;查看sadd的元素长度，如没有返回0  
   4、sdiff		&emsp;返回两个sadd不同的元素值  
   5、sismember	&emsp;判断member元素中是否有这个值，没有返回0，有返回1     
   6、smove		&emsp;移除一个元素  
   7、spop		&emsp;移除一个随机的元素  
   8、srandmember	&emsp;返回一个随机的元素，但不会删除元素  
   9、srem		&emsp;可以同时删除多个元素或一个  
   10、sunion	&emsp;两个key相连在一起，如果有相同的值，只会要一个  
   11、sunionstore	&emsp;多个key相连在一起，如果有相同的值，只会要一个  
   12、sscan 	  
   ### 六、
