package top.zhenxun.blogs.api.utils;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
public class SnowflakeIdWorker {

    // 数据中心ID和机器ID（假设配置为0，0）
    private static final long DATA_CENTER_ID = 0L;
    private static final long MACHINE_ID = 0L;

    // 起始时间戳 (2023-01-01)
    private static final long START_TIMESTAMP = 1672444800000L;

    // 每一部分占用的位数
    private static final long SEQUENCE_BITS = 4L; // 序列号的位数（调整为4位）
    private static final long MACHINE_ID_BITS = 2L; // 机器ID的位数（调整为2位）
    private static final long DATA_CENTER_ID_BITS = 2L; // 数据中心ID的位数（调整为2位）

    // 每一部分的最大值
    private static final long MAX_MACHINE_ID = -1L ^ (-1L << MACHINE_ID_BITS);
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);

    // 移动位数
    private static final long MACHINE_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS + DATA_CENTER_ID_BITS;

    // 序列号
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    /**
     * 生成雪花算法ID
     * @return 唯一ID（12位）
     */
    public synchronized static long getId() {
        long timestamp = System.currentTimeMillis();

        // 如果是同一毫秒内，序列号+1
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & (-1L ^ (-1L << SEQUENCE_BITS));
            if (sequence == 0) {
                // 序列号用尽，等待下一毫秒
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒，重置序列号
            sequence = 0;
        }

        lastTimestamp = timestamp;

        // 生成12位ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_SHIFT) |
                (DATA_CENTER_ID << DATA_CENTER_ID_SHIFT) |
                (MACHINE_ID << MACHINE_ID_SHIFT) |
                sequence;
    }

    private static long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}