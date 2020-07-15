package com.example.demo.test;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class SentinelTest {

	public static void main(String[] args) {
		testDegrade(args);
	}

	/**
	 * Sentinel 在使用上提供了两种形式，一种是异常捕获形式，一种是布尔形式。也就是当限流被触发时，是抛出异常来还是返回一个 false。下面我们看看它的异常捕获形式，这是单机版
	 * @param args
	 */
	public static void testException(String[] args) {
		// 配置规则
		List<FlowRule> rules = new ArrayList<>();
		FlowRule rule = new FlowRule();
		rule.setResource("tutorial");
		// QPS 不得超出 1
		rule.setCount(1);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setLimitApp("default");
		rules.add(rule);
        // 加载规则
		FlowRuleManager.loadRules(rules);
        // 下面开始运行被限流作用域保护的代码
		while (true) {
			Entry entry = null;
			try {
				entry = SphU.entry("tutorial1");
				System.out.println("hello world");
			} catch (BlockException e) {
				System.out.println("blocked");
			} finally {
				if (entry != null) {
					entry.exit();
				}
			}
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {}
		}
	}

	/**
	 * 我们再看看它的 bool 形式，使用也是很简单，大同小异。
	 * @param args
	 */
	public static void testBoolean(String[] args) {
		// 配置规则
		List<FlowRule> rules = new ArrayList<>();
		FlowRule rule = new FlowRule();
		rule.setResource("tutorial");
		// QPS 不得超出 1
		rule.setCount(1);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setLimitApp("default");
		rules.add(rule);
		FlowRuleManager.loadRules(rules);
		// 运行被限流作用域保护的代码
		while (true) {
			if (SphO.entry("tutorial")) {
				try {
					System.out.println("hello world");
				} finally {
					SphO.exit();
				}
			} else {
				System.out.println("blocked");
			}
			try {
				Thread.sleep(170);
			} catch (InterruptedException e) {}
		}
	}

	/**
	 * 限流在于限制流量，也就是 QPS 或者线程的并发数，还有一种情况是请求处理不稳定或者服务损坏，
	 * 导致请求处理时间过长或者老是频繁抛出异常，这时就需要对服务进行降级处理。所谓的降级处理和限流处理在形式上没有明显差异，
	 * 也是以同样的形式定义一个临界区，区别是需要对抛出来的异常需要进行统计，这样才可以知道请求异常的频率，有了这个指标才会触发降级。
	 * @param args
	 */
	public static void testDegrade(String[] args) {
		// 定义降级规则
		List<DegradeRule> rules = new ArrayList<>();
		DegradeRule rule = new DegradeRule();
		rule.setResource("tutorial");
		rule.setTimeWindow(5);
// 5s  ?   内异常不得超出10
		rule.setCount(10);
		rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
		rule.setLimitApp("default");
		rules.add(rule);
		DegradeRuleManager.loadRules(rules);

		Entry entry = null;
		while (true){
			try {
				entry = SphU.entry("tutorial");
				// 业务代码在这里
				System.out.println("hello world");
			} catch (Throwable t) {
				// 记录异常
				System.out.println("degraded");
				if (!BlockException.isBlockException(t)) {
					Tracer.trace(t);
				}
			} finally {
				if (entry != null) {
					entry.exit();
				}
			}
		}

	}

}
