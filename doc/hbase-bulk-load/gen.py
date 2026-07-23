import csv
import random
import time
from faker import Faker

# 初始化生成器，生成中文姓名
fake = Faker("zh_CN")
# 生成1000个不重复的人名
unique_names = set()
while len(unique_names) < 1000:
    unique_names.add(fake.name())
name_list = list(unique_names)

# 预设业务枚举值
bank_names = ["中国工商银行", "中国建设银行", "中国农业银行", "中国银行", "招商银行", "交通银行", "浦发银行", "民生银行"]
pay_channels = ["手机银行", "网银转账", "第三方支付", "柜台转账", "ATM转账"]
pay_ways = ["实时到账", "普通转账", "延时转账"]
status_list = ["转账成功", "转账处理中", "转账失败", "待确认"]
comments = ["货款", "往来款", "报销", "工资", "借款", "服务费", "备用金", ""]

# 目标文件路径
target_file = "transfer_records_1G.csv"

# 写入表头
with open(target_file, "w", newline="", encoding="utf-8-sig") as f:
    writer = csv.writer(f)
    writer.writerow(["id", "code", "rec_account", "rec_bank_name", "rec_name", "pay_account", "pay_name", "pay_comments", "pay_channel", "pay_way", "status", "timestamp", "money"])

# 循环写入数据直到文件大小接近1GB
current_id = 1
while True:
    # 批量生成1000条数据后检查文件大小
    batch_data = []
    for _ in range(1000):
        rec_name = random.choice(name_list)
        pay_name = random.choice(name_list)
        while pay_name == rec_name:
            pay_name = random.choice(name_list)
        batch_data.append([
            current_id,
            f"TX{int(time.time()*1000000) + current_id}",
            f"6222{random.randint(100000000000, 999999999999)}",
            random.choice(bank_names),
            rec_name,
            f"6222{random.randint(1000000000000, 999999999999)}",
            pay_name,
            random.choice(comments),
            random.choice(pay_channels),
            random.choice(pay_ways),
            random.choice(status_list),
            f"2026-{random.randint(1,6):02d}-{random.randint(1,28):02d} {random.randint(0,23):02d}:{random.randint(0,59):02d}:{random.randint(0,59):02d}",
            round(random.uniform(0.01, 999999.99), 2)
        ])
        current_id += 1

    # 追加写入批量数据
    with open(target_file, "a", newline="", encoding="utf-8-sig") as f:
        writer = csv.writer(f)
        writer.writerows(batch_data)

    # 检查文件大小，达到1GB左右停止
    import os
    if os.path.getsize(target_file) >= 1024 * 1024 * 1024:
        break

print(f"生成完成，文件路径：{target_file}，总记录数：{current_id-1}")
