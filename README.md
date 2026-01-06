# FRC Junior Academy 2025 - 编程控制单元作业

## 项目简介

这是FRC Junior Academy 2025年编程控制单元的作业仓库，主题为**三人囚徒困境**游戏策略设计。

通过这个项目，学生将学习博弈论基础，设计Java策略算法，理解FRC比赛中"Coopertition"（合作竞争）的核心理念。

## 仓库结构

```
├── README.md                      # 仓库说明文档（本文件）
├── 编程控制单元作业.md             # 详细作业要求与教程
├── 编程控制单元作业.pdf            # 作业要求PDF版本
├── ThreePrisonersDilemma.java    # 游戏测试框架
└── 示例submission/                # 提交示例
    ├── GongZeruiPlayer.java      # 示例策略代码
    └── 龚泽睿.txt                 # 示例说明文档
```

## 快速开始

### 1. 阅读作业要求

详细的作业说明、游戏规则、策略示例等内容请查看：
- **[编程控制单元作业.md](编程控制单元作业.md)** - Markdown格式（推荐）
- **[编程控制单元作业.pdf](编程控制单元作业.pdf)** - PDF格式

### 2. 创建你的策略

参考示例文件 `示例submission/GongZeruiPlayer.java`，创建你自己的策略类：

```java
public class YourNamePlayer extends ThreePrisonersDilemma.Player {
    @Override
    int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
        // 在这里实现你的策略
        return 0; // 0=合作, 1=背叛
    }
}
```

### 3. 测试你的策略

```bash
# 编译
javac ThreePrisonersDilemma.java YourNamePlayer.java

# 运行测试
java ThreePrisonersDilemma
```

### 4. 提交作业

需要提交两个文件：
- `YourNamePlayer.java` - 你的策略代码
- `你的中文姓名.txt` - 策略说明（可选）

## 关键概念

**三人囚徒困境收益矩阵：**

| 我的选择 | 对手1 | 对手2 | 我的得分 |
|---------|------|------|---------|
| 合作 | 合作 | 合作 | **6分** |
| 合作 | 合作 | 背叛 | 3分 |
| 合作 | 背叛 | 合作 | 3分 |
| 合作 | 背叛 | 背叛 | **0分**（最低） |
| 背叛 | 合作 | 合作 | **8分**（最高） |
| 背叛 | 合作 | 背叛 | 5分 |
| 背叛 | 背叛 | 合作 | 5分 |
| 背叛 | 背叛 | 背叛 | 2分 |

设计策略时需要在**合作共赢**和**个人最优**之间找到平衡！

## 学习资源

- 详细教程和策略示例：查看 [编程控制单元作业.md](编程控制单元作业.md)
- 鼓励使用AI工具（DeepSeek、Kimi、Qwen等）辅助学习和优化策略
- 可以与同学讨论，但代码必须独立完成

## 评分标准

- **策略效果 (60%)**：锦标赛表现
- **代码质量 (25%)**：可读性、注释、结构
- **创新性 (15%)**：原创性和思考深度

---

祝你好运！记住，最好的策略往往不是最复杂的，而是最适合的。
