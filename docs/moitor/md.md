# moitor服务

监控服务器后传入时序数据库

1. cpu

- 温度

# 监控方法

## linux文件夹proc

[介绍参数](https://man7.org/linux/man-pages/man5/proc.5.html)

- cat /proc/stat
    - cpu运行时间等信息
- cat /proc/uptime
    - 运行内存的相关信息
    - https://www.cnblogs.com/elisha-blogs/p/uptime.html
- cat /proc/cpuinfo
    - 查看cpu处理器信息
- cat /proc/meminfo
    - 查看内存

## /proc/stat

### cpu

1. user：表示用户态（User Mode）中CPU的使用时间。用户态指的是应用程序或用户进程在执行时所处的状态。
2. nice：表示调整过的用户态CPU的使用时间。它是用户进程在较低优先级下的CPU使用时间。通过调整优先级，可以控制进程对CPU资源的占用。
3. system：表示内核态（Kernel Mode）中CPU的使用时间。内核态指的是操作系统内核在执行时所处的状态。
4. idle：表示CPU处于空闲状态的时间。空闲状态意味着CPU没有要处理的任务，它处于等待新任务到来的状态。
5. iowait：表示CPU等待输入/输出完成的时间。当CPU发起了一个I/O请求后，它需要等待I/O设备的响应，这段等待的时间就是iowait时间。
6. irq：表示处理硬件中断的时间。硬件中断是由外部设备触发的，如键盘、鼠标、网络接口等。
7. softirq：表示处理软中断的时间。软中断是由内核自身触发的一种处理机制，用于处理与网络、磁盘IO、定时器等相关的任务。
8.

steal：表示被虚拟化环境（如虚拟机）偷取的CPU时间。在虚拟化环境中，多个虚拟机共享物理CPU资源，当一个虚拟机无法立即获得CPU时间时，就会从其他虚拟机中"
偷取"一些CPU时间。

9. guest：表示为虚拟机执行客户操作系统代码所花费的时间。当物理机器上运行虚拟机时，虚拟机会占用一部分 CPU 时间来执行其自己的客户操作系统代码。
10. guest_nice：调整过的虚拟机执行时间。类似于nice参数，此处表示虚拟机在较低优先级下执行的时间。