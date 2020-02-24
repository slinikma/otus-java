-Xms128m -Xmx128m -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=70
--------------------------------------------------------------------------------------------------------------------------------
``````
00:19:24.551 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 0, Time (ms): 0, MajorGC -> Count: 0, Time (ms): 0
00:20:24.537 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 1, Time (ms): 111, MajorGC -> Count: 0, Time (ms): 0
00:21:24.543 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 3, Time (ms): 186, MajorGC -> Count: 0, Time (ms): 0
00:22:24.546 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 5, Time (ms): 402, MajorGC -> Count: 1, Time (ms): 15
00:23:24.540 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 6, Time (ms): 406, MajorGC -> Count: 4, Time (ms): 173
00:24:24.553 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 6, Time (ms): 406, MajorGC -> Count: 4, Time (ms): 173
``````

## Вывод:
``````
CMS позывает минимальные значения задержки на сборке старого поколения
сборка молодого поколения происходит быстрее чем у ParallelGC и SerialGC, но медленнее, чем у G1
``````

-Xms128m -Xmx128m -XX:+UseParallelGC -XX:+UseParallelOldGC
--------------------------------------------------------------------------------------------------------------------------------
``````
00:46:28.615 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 0, Time (ms): 0, MajorGC -> Count: 0, Time (ms): 0
00:47:28.605 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 2, Time (ms): 127, MajorGC -> Count: 0, Time (ms): 0
00:48:28.605 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 3, Time (ms): 191, MajorGC -> Count: 0, Time (ms): 0
00:49:28.604 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 4, Time (ms): 287, MajorGC -> Count: 0, Time (ms): 0
00:50:28.603 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 7, Time (ms): 715, MajorGC -> Count: 1, Time (ms): 315
00:51:28.605 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 7, Time (ms): 715, MajorGC -> Count: 2, Time (ms): 510
``````

## Вывод:
``````
ParallelGC  явно медленнее чем CMS и G1 как при сборке молодого, так и старого поколения

Странно, но он выглядит так-же медленнее, чем SerialGC
``````

-Xms128m -Xmx128m -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=70
--------------------------------------------------------------------------------------------------------------------------------
``````
01:05:03.217 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 1, Time (ms): 8, MajorGC -> Count: 0, Time (ms): 0
01:06:03.210 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 3, Time (ms): 40, MajorGC -> Count: 0, Time (ms): 0
01:07:03.207 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 4, Time (ms): 48, MajorGC -> Count: 0, Time (ms): 0
01:08:03.208 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 5, Time (ms): 52, MajorGC -> Count: 0, Time (ms): 0
01:09:03.207 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 7, Time (ms): 73, MajorGC -> Count: 0, Time (ms): 0
01:10:03.212 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 8, Time (ms): 88, MajorGC -> Count: 0, Time (ms): 0
01:11:03.211 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 10, Time (ms): 101, MajorGC -> Count: 1, Time (ms): 256
01:12:03.227 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 13, Time (ms): 127, MajorGC -> Count: 1, Time (ms): 256
``````

## Вывод:
``````
G1 самый быстрый при сборке молодого поколения, но медленее чем CMS при сборке старого поколения
``````

-Xms128m -Xmx128m -XX:+UseSerialGC
--------------------------------------------------------------------------------------------------------------------------------
``````
01:22:04.508 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 0, Time (ms): 0, MajorGC -> Count: 0, Time (ms): 0
01:23:04.499 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 0, Time (ms): 0, MajorGC -> Count: 0, Time (ms): 0, UnknownGC -> Count: 2, Time (ms): 115
01:24:04.501 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 0, Time (ms): 0, MajorGC -> Count: 0, Time (ms): 0, UnknownGC -> Count: 3, Time (ms): 153
01:25:04.504 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 0, Time (ms): 0, MajorGC -> Count: 0, Time (ms): 0, UnknownGC -> Count: 5, Time (ms): 345
01:26:04.509 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 0, Time (ms): 0, MajorGC -> Count: 0, Time (ms): 0, UnknownGC -> Count: 7, Time (ms): 472
01:27:04.507 [Timer-0] INFO  ru.otus.Benchmark - GC metrics: MinorGC -> Count: 0, Time (ms): 0, MajorGC -> Count: 0, Time (ms): 0, UnknownGC -> Count: 9, Time (ms): 632
``````

## Вывод:
``````
SerialGC явно медленнее чем G1 и медленнее чем CMS
``````

# Основные выводы:
``````
Throughout:
G1 на первом месте по throughout сборки молодого поколения, но он проигрывает CMS по throughout сборки старого, это скорее всего связано с тем, что я создаю небольшие объекты размером в 2000 байт,
а при сборке старого G1 производит дефрагментацию блоков.

Latency:
G1 так-же имеет минимальный latency при сборке молодого поколения (~ 11ms). У CMS ~ 70ms
С другой стороны, CMS имеет меньшую latency при сборке старого поколения, чем G1: ~ 45ms притив ~ 250ms

ParallelGC и SerialGC производят меньшее количество сборок, но общее время сборок больше чем у G1 и CMS.



В моём случае на объектах размером в 2000 байт самый лучший варинт - это G1.
``````
