package org.geogebra.common.gui.view.table.dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.geogebra.common.kernel.Kernel;
import org.geogebra.common.kernel.StringTemplate;
import org.geogebra.common.kernel.arithmetic.Command;
import org.geogebra.common.kernel.commands.AlgebraProcessor;
import org.geogebra.common.kernel.kernelND.GeoElementND;
import org.geogebra.common.kernel.kernelND.GeoEvaluatable;
import org.geogebra.common.kernel.statistics.Stat;

public class StatsBuilder {

	private final Kernel kernel;
	private final GeoEvaluatable[] lists;
	final static List<Stat> ONE_VAR_STATS = Arrays.asList(
			Stat.MEAN, Stat.SUM, Stat.SIGMAXX, Stat.SAMPLE_SD, Stat.SD);
	final static List<Stat> ONE_VAR_EXTRA = Arrays.asList(Stat.LENGTH,
			Stat.MIN, Stat.Q1, Stat.MEDIAN, Stat.Q3, Stat.MAX
	);
	final static List<Stat> MIN_MAX = Arrays.asList(Stat.MIN, Stat.MAX);
	final static List<Stat> TWO_VAR_STATS = Arrays.asList(
			Stat.SIGMAXY, Stat.PMCC, Stat.COVARIANCE
	);

	/**
	 * @param dataLists data lists
	 */
	public StatsBuilder(GeoEvaluatable... dataLists) {
		this.lists = dataLists;
		this.kernel = lists[0].getKernel();
	}

	/**
	 * @return single variable statistics
	 */
	public List<StatisticRow> getStatistics() {
		List<StatisticRow> stats = new ArrayList<>();
		// use command strings, not algos, to make sure code splitting works in Web

		addStats(stats, ONE_VAR_STATS, "x", lists[0]);
		addStats(stats, ONE_VAR_EXTRA, "x", lists[0]);
		return stats;
	}

	/**
	 * @return two variable statistics
	 */
	public List<StatisticRow> getStatistics2() {
		List<StatisticRow> stats = new ArrayList<>();
		// use command strings, not algos, to make sure code splitting works in Web
		addStats(stats, ONE_VAR_STATS, "x", lists[0]);
		addStats(stats, ONE_VAR_STATS, "y", lists[1]);
		addStats(stats, TWO_VAR_STATS, "", lists);
		addStats(stats, Arrays.asList(Stat.LENGTH), "x", lists[0]);
		addStats(stats, MIN_MAX, "x", lists[0]);
		addStats(stats, MIN_MAX, "y", lists[1]);
		return stats;
	}

	private void addStats(List<StatisticRow> stats, List<Stat> statAlgos, String varName,
			GeoEvaluatable... lists) {
		for (Stat cmd: statAlgos) {
			Command exec = new Command(kernel, cmd.getCommandName(), false);
			for (GeoEvaluatable list: lists) {
				exec.addArgument(list.wrap());
			}
			try {
				AlgebraProcessor algebraProcessor = kernel.getAlgebraProcessor();
				GeoElementND result = algebraProcessor.processValidExpressionSilent(exec)[0];
				String heading = cmd.getLocalizedName(kernel.getLocalization());
				String lhs = cmd.getLHS(kernel.getLocalization(), varName);
				stats.add(new StatisticRow(heading,
						lhs + " = " + result.toValueString(StringTemplate.defaultTemplate)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
