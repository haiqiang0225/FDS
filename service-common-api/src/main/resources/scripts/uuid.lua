--- 获取key前缀
local keyPrefix = KEYS[1]
--- 获取指定的需要自增的部分
local incrKey = keyPrefix .. "-" .. KEYS[2]
--- 获取单个段的最大值
local segMaxValue = tonumber(ARGV[1])

--- 检查值是否存在，如果不存在，设置一个随机值，如果存在，返回对应的值
local function checkAndSetVal(key)
    local val = redis.call('get', key)
    if val == false then
        local randomVal = math.random(0, math.floor(segMaxValue / 3))
        redis.call('set', key, randomVal)
        val = randomVal
    end

    return val
end

local results = {}

--- 获取增量
local incr = tonumber(ARGV[2])

local oldValue = redis.call('get', incrKey)
local newValue = incr

if oldValue ~= false then
    newValue = oldValue + incr
else
    newValue = checkAndSetVal(incrKey)
end

--- 如果溢出了
if newValue > segMaxValue then
    results[1] = "overflow"
    --error(incrKey .. "overflow")
else
    local res = redis.call('set', incrKey, newValue)
    results[1] = res
    local segKey1 = keyPrefix .. "-1"
    local segKey2 = keyPrefix .. "-2"
    local segKey3 = keyPrefix .. "-3"
    local segKey4 = keyPrefix .. "-4"

    local val1 = checkAndSetVal(segKey1)
    local val2 = checkAndSetVal(segKey2)
    local val3 = checkAndSetVal(segKey3)
    local val4 = checkAndSetVal(segKey4)

    results[2] = val1
    results[3] = val2
    results[4] = val3
    results[5] = val4
    return results
end

