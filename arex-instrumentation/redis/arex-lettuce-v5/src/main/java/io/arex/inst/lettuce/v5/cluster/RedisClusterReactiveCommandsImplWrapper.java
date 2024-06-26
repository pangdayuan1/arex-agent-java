package io.arex.inst.lettuce.v5.cluster;

import io.arex.inst.redis.common.RedisConnectionManager;
import io.arex.inst.redis.common.RedisKeyUtil;
import io.arex.inst.redis.common.lettuce.wrapper.RedisReactiveCommandWrapper;
import io.lettuce.core.KeyValue;
import io.lettuce.core.SetArgs;
import io.lettuce.core.cluster.RedisAdvancedClusterReactiveCommandsImpl;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.output.KeyStreamingChannel;
import io.lettuce.core.output.KeyValueStreamingChannel;
import io.lettuce.core.output.ValueStreamingChannel;
import io.lettuce.core.protocol.CommandType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;

/**
 * RedisClusterReactiveCommandsImplWrapper
 */
public class RedisClusterReactiveCommandsImplWrapper<K, V> extends RedisAdvancedClusterReactiveCommandsImpl<K, V> {

    private final RedisReactiveCommandWrapper<K, V> reactiveCommandsWrapper;
    private String redisUri;

    /**
     * Initialize a new instance.
     *
     * @param connection the connection to operate on.
     * @param codec      the codec for command encoding.
     */
    public RedisClusterReactiveCommandsImplWrapper(StatefulRedisClusterConnection<K, V> connection,
        RedisCodec<K, V> codec) {
        super(connection, codec);
        this.reactiveCommandsWrapper = new RedisReactiveCommandWrapper<>(codec);
    }

    @Override
    public Mono<Long> append(K key, V value) {
        return reactiveCommandsWrapper.append(this, getRedisUri(), key, value);
    }

    @Override
    public Mono<Long> decr(K key) {
        return reactiveCommandsWrapper.decr(this, getRedisUri(), key);
    }

    @Override
    public Mono<Long> decrby(K key, long amount) {
        return reactiveCommandsWrapper.decrby(this, getRedisUri(), key, amount);
    }

    @Override
    public Mono<Boolean> expire(K key, long seconds) {
        return reactiveCommandsWrapper.expire(this, getRedisUri(), key, seconds);
    }

    @Override
    public Mono<Boolean> expireat(K key, long timestamp) {
        return reactiveCommandsWrapper.expireat(this, getRedisUri(), key, timestamp);
    }

    @Override
    public Mono<Boolean> expireat(K key, Date timestamp) {
        return reactiveCommandsWrapper.expireat(this, getRedisUri(), key, timestamp);
    }

    @Override
    public Mono<V> get(K key) {
        return reactiveCommandsWrapper.get(this, getRedisUri(), key);
    }

    @Override
    public Mono<Long> getbit(K key, long offset) {
        return reactiveCommandsWrapper.getbit(this, getRedisUri(), key, offset);
    }

    @Override
    public Mono<V> getrange(K key, long start, long end) {
        return reactiveCommandsWrapper.getrange(this, getRedisUri(), key, start, end);
    }

    @Override
    public Mono<V> getset(K key, V value) {
        return reactiveCommandsWrapper.getset(this, getRedisUri(), key, value);
    }

    @Override
    public Mono<Long> hdel(K key, K... fields) {
        return reactiveCommandsWrapper.hdel(this, getRedisUri(), key, fields);
    }

    @Override
    public Mono<Boolean> hexists(K key, K field) {
        return reactiveCommandsWrapper.hexists(this, getRedisUri(), key, field);
    }

    @Override
    public Mono<V> hget(K key, K field) {
        return reactiveCommandsWrapper.hget(this, getRedisUri(), key, field);
    }

    @Override
    public Mono<Map<K, V>> hgetall(K key) {
        return reactiveCommandsWrapper.hgetallMono(this, getRedisUri(), key);
    }

    @Override
    public Mono<Long> hgetall(KeyValueStreamingChannel<K, V> channel, K key) {
        return reactiveCommandsWrapper.hgetall(this, getRedisUri(), channel, key);
    }

    @Override
    public Mono<Long> hincrby(K key, K field, long amount) {
        return reactiveCommandsWrapper.hincrby(this, getRedisUri(), key, field, amount);
    }

    @Override
    public Mono<Double> hincrbyfloat(K key, K field, double amount) {
        return reactiveCommandsWrapper.hincrbyfloat(this, getRedisUri(), key, field, amount);
    }

    @Override
    public Flux<K> hkeys(K key) {
        return reactiveCommandsWrapper.hkeys(this, getRedisUri(), key);
    }

    @Override
    public Mono<Long> hkeys(KeyStreamingChannel<K> channel, K key) {
        return reactiveCommandsWrapper.hkeys(this, getRedisUri(), channel, key);
    }

    @Override
    public Mono<Long> hlen(K key) {
        return reactiveCommandsWrapper.hlen(this, getRedisUri(), key);
    }

    @Override
    public Flux<KeyValue<K, V>> hmget(K key, K... fields) {
        return reactiveCommandsWrapper.hmget(this, getRedisUri(), key, fields);
    }

    @Override
    public Mono<Long> hmget(KeyValueStreamingChannel<K, V> channel, K key, K... fields) {
        return reactiveCommandsWrapper.hmget(this, getRedisUri(), channel, key, fields);
    }

    @Override
    public Mono<String> hmset(K key, Map<K, V> map) {
        return reactiveCommandsWrapper.hmset(this, getRedisUri(), key, map);
    }

    @Override
    public Mono<Boolean> hset(K key, K field, V value) {
        return reactiveCommandsWrapper.hset(this, getRedisUri(), key, field, value);
    }

    @Override
    public Mono<Long> hset(K key, Map<K, V> map) {
        return reactiveCommandsWrapper.hset(this, getRedisUri(), key, map);
    }


    @Override
    public Mono<Boolean> hsetnx(K key, K field, V value) {
        return reactiveCommandsWrapper.hsetnx(this, getRedisUri(), key, field, value);
    }

    @Override
    public Flux<V> hvals(K key) {
        return reactiveCommandsWrapper.hvals(this, getRedisUri(), key);
    }

    @Override
    public Mono<Long> hvals(ValueStreamingChannel<V> channel, K key) {
        return reactiveCommandsWrapper.hvals(this, getRedisUri(), channel, key);
    }

    @Override
    public Mono<Long> incr(K key) {
        return reactiveCommandsWrapper.incr(this, getRedisUri(), key);
    }

    @Override
    public Mono<Long> incrby(K key, long amount) {
        return reactiveCommandsWrapper.incrby(this, getRedisUri(), key, amount);
    }

    @Override
    public Mono<Double> incrbyfloat(K key, double amount) {
        return reactiveCommandsWrapper.incrbyfloat(this, getRedisUri(), key, amount);
    }

    @Override
    public Mono<V> lindex(K key, long index) {
        return reactiveCommandsWrapper.lindex(this, getRedisUri(), key, index);
    }

    @Override
    public Mono<Long> llen(K key) {
        return reactiveCommandsWrapper.llen(this, getRedisUri(), key);
    }

    @Override
    public Mono<V> lpop(K key) {
        return reactiveCommandsWrapper.lpop(this, getRedisUri(), key);
    }

    @Override
    public Flux<V> lrange(K key, long start, long stop) {
        return reactiveCommandsWrapper.lrange(this, getRedisUri(), key, start, stop);
    }

    @Override
    public Mono<Long> lrange(ValueStreamingChannel<V> channel, K key, long start, long stop) {
        return reactiveCommandsWrapper.lrange(this, getRedisUri(), channel, key, start, stop);
    }

    @Override
    public Mono<String> lset(K key, long index, V value) {
        return reactiveCommandsWrapper.lset(this, getRedisUri(), key, index, value);
    }

    @Override
    public Mono<String> ltrim(K key, long start, long stop) {
        return reactiveCommandsWrapper.ltrim(this, getRedisUri(), key, start, stop);
    }

    @Override
    public Mono<Boolean> persist(K key) {
        return reactiveCommandsWrapper.persist(this, getRedisUri(), key);
    }

    @Override
    public Mono<Boolean> pexpire(K key, long milliseconds) {
        return reactiveCommandsWrapper.pexpire(this, getRedisUri(), key, milliseconds);
    }


    @Override
    public Mono<Boolean> pexpireat(K key, Date timestamp) {
        return reactiveCommandsWrapper.pexpireat(this, getRedisUri(), key, timestamp);
    }

    @Override
    public Mono<Boolean> pexpireat(K key, long timestamp) {
        return reactiveCommandsWrapper.pexpireat(this, getRedisUri(), key, timestamp);
    }

    @Override
    public Mono<String> psetex(K key, long milliseconds, V value) {
        return reactiveCommandsWrapper.psetex(this, getRedisUri(), key, milliseconds, value);
    }

    @Override
    public Mono<Long> pttl(K key) {
        return reactiveCommandsWrapper.pttl(this, getRedisUri(), key);
    }

    @Override
    public Mono<String> rename(K key, K newKey) {
        return reactiveCommandsWrapper.rename(this, getRedisUri(), key, newKey);
    }

    @Override
    public Mono<Boolean> renamenx(K key, K newKey) {
        return reactiveCommandsWrapper.renamenx(this, getRedisUri(), key, newKey);
    }

    @Override
    public Mono<V> rpop(K key) {
        return reactiveCommandsWrapper.rpop(this, getRedisUri(), key);
    }

    @Override
    public Mono<V> rpoplpush(K source, K destination) {
        return reactiveCommandsWrapper.rpoplpush(this, getRedisUri(), source, destination);
    }

    @Override
    public Mono<Long> scard(K key) {
        return reactiveCommandsWrapper.scard(this, getRedisUri(), key);
    }

    @Override
    public Flux<V> sdiff(K... keys) {
        return reactiveCommandsWrapper.sdiff(this, getRedisUri(), keys);
    }

    @Override
    public Mono<Long> sdiff(ValueStreamingChannel<V> channel, K... keys) {
        return reactiveCommandsWrapper.sdiff(this, getRedisUri(), channel, keys);
    }

    @Override
    public Mono<String> set(K key, V value) {
        return reactiveCommandsWrapper.set(this, getRedisUri(), key, value);
    }

    @Override
    public Mono<String> set(K key, V value, SetArgs setArgs) {
        return reactiveCommandsWrapper.set(this, getRedisUri(), key, value, setArgs);
    }

    @Override
    public Mono<String> setex(K key, long seconds, V value) {
        return reactiveCommandsWrapper.setex(this, getRedisUri(), key, seconds, value);
    }

    @Override
    public Mono<Boolean> setnx(K key, V value) {
        return reactiveCommandsWrapper.setnx(this, getRedisUri(), key, value);
    }

    @Override
    public Mono<Long> setrange(K key, long offset, V value) {
        return reactiveCommandsWrapper.setrange(this, getRedisUri(), key, offset, value);
    }

    @Override
    public Flux<V> sinter(K... keys) {
        return reactiveCommandsWrapper.sinter(this, getRedisUri(), keys);
    }

    @Override
    public Mono<Long> sinter(ValueStreamingChannel<V> channel, K... keys) {
        return reactiveCommandsWrapper.sinter(this, getRedisUri(), channel, keys);
    }

    @Override
    public Mono<V> spop(K key) {
        return reactiveCommandsWrapper.spop(this, getRedisUri(), key);
    }

    @Override
    public Flux<V> spop(K key, long count) {
        return reactiveCommandsWrapper.spop(this, getRedisUri(), key, count);
    }

    @Override
    public Mono<V> srandmember(K key) {
        return reactiveCommandsWrapper.srandmember(this, getRedisUri(), key);
    }

    @Override
    public Flux<V> srandmember(K key, long count) {
        return reactiveCommandsWrapper.srandmember(this, getRedisUri(), key, count);
    }

    @Override
    public Mono<Long> srandmember(ValueStreamingChannel<V> channel, K key, long count) {
        return reactiveCommandsWrapper.srandmember(this, getRedisUri(), channel, key, count);
    }

    @Override
    public Mono<Long> strlen(K key) {
        return reactiveCommandsWrapper.strlen(this, getRedisUri(), key);
    }

    @Override
    public Flux<V> sunion(K... keys) {
        return reactiveCommandsWrapper.sunion(this, getRedisUri(), keys);
    }

    @Override
    public Mono<Long> sunion(ValueStreamingChannel<V> channel, K... keys) {
        return reactiveCommandsWrapper.sunion(this, getRedisUri(), channel, keys);
    }

    @Override
    public Mono<Long> ttl(K key) {
        return reactiveCommandsWrapper.ttl(this, getRedisUri(), key);
    }

    @Override
    public Mono<String> type(K key) {
        return reactiveCommandsWrapper.type(this, getRedisUri(), key);
    }

    @Override
    public Mono<Long> zcard(K key) {
        return reactiveCommandsWrapper.zcard(this, getRedisUri(), key);
    }

    @Override
    public Mono<Long> del(Iterable<K> keys) {
        return reactiveCommandsWrapper.createMono(() -> super.del(keys), CommandType.DEL.name(), getRedisUri(),
            RedisKeyUtil.generate(keys));
    }

    @Override
    public Mono<Long> exists(Iterable<K> keys) {
        return reactiveCommandsWrapper.createMono(() -> super.exists(keys), CommandType.EXISTS.name(), getRedisUri(),
            RedisKeyUtil.generate(keys));
    }

    @Override
    public Flux<K> keys(K pattern) {
        return reactiveCommandsWrapper.createFlux(() -> super.keys(pattern), CommandType.KEYS.name(), getRedisUri(),
            RedisKeyUtil.generate(pattern));
    }

    @Override
    public Mono<Long> keys(KeyStreamingChannel<K> channel, K pattern) {
        return reactiveCommandsWrapper.createMono(() -> super.keys(channel, pattern), CommandType.KEYS.name(),
            getRedisUri(),
            RedisKeyUtil.generate(pattern));
    }

    @Override
    public Flux<KeyValue<K, V>> mget(Iterable<K> keys) {
        return reactiveCommandsWrapper.createFlux(() -> super.mget(keys), CommandType.MGET.name(), getRedisUri(),
            RedisKeyUtil.generate(keys));
    }

    @Override
    public Mono<Long> mget(KeyValueStreamingChannel<K, V> channel, Iterable<K> keys) {
        return reactiveCommandsWrapper.createMono(() -> super.mget(channel, keys), CommandType.MGET.name(),
            getRedisUri(),
            RedisKeyUtil.generate(keys));
    }

    @Override
    public Mono<Boolean> msetnx(Map<K, V> map) {
        return reactiveCommandsWrapper.createMono(() -> super.msetnx(map), CommandType.MSETNX.name(), getRedisUri(),
            RedisKeyUtil.generate(map));
    }

    private String getRedisUri() {
        if (redisUri == null) {
            redisUri = RedisConnectionManager.getRedisUri(this.getStatefulConnection().hashCode());
        }
        return redisUri;
    }
}
