package org.eredlab.g4.ccl.id.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.id.generator.DefaultIDGenerator;
import org.eredlab.g4.ccl.id.sequence.DefaultSequenceGenerator;
import org.eredlab.g4.ccl.id.sequence.TimeRollingSequenceGenerator;

/**
 * Hello
 * �˴���Դ�ڿ�Դ��ĿE3,ԭ���ߣ����ƻ�
 * 
 * @author XiongChun
 * @since 2010-03-17
 */
public class Hello {
	private static final Log logger = LogFactory.getLog(Hello.class);

	public static void main(String[] args) {
		// //����������
		// DefaultIDGenerator generator = new DefaultIDGenerator();
		//	
		// //ǰ׺�����������ڲ���IDǰ׺
		// DefaultPrefixGenerator prefixGenerator = new
		// DefaultPrefixGenerator();
		// prefixGenerator.setWithDate(false);
		// generator.setPrefixGenerator(prefixGenerator);
		// prefixGenerator.setPrefix("E3_");
		//	
		// //���������
		// DefaultSequenceGenerator sequenceGenerator =
		// TimeRollingSequenceGenerator.getYearRollingSequenceGenerator();
		// sequenceGenerator.setMinValue(0);
		// sequenceGenerator.setMaxValue(9999999);
		// sequenceGenerator.setCycle(true);
		// sequenceGenerator.setCache(100);
		// generator.setSequenceGenerator(sequenceGenerator);
		//	
		// //��ʽ�����
		// DefaultSequenceFormater sequenceFormater = new
		// DefaultSequenceFormater();
		// sequenceFormater.setPattern("0000000");
		// generator.setSequenceFormater(sequenceFormater);
		// System.out.println(generator.create());

		DefaultIDGenerator generator = new DefaultIDGenerator();
		DefaultSequenceGenerator sequenceGenerator = TimeRollingSequenceGenerator
				.getDayRollingSequenceGenerator();
		sequenceGenerator.setMinValue(1000000);
		sequenceGenerator.setMaxValue(9999999);
		generator.setSequenceGenerator(sequenceGenerator);
		System.out.println(generator.create());
		logger.debug("dddddddddddd");

		// net.jcreate.e3.commons.id.sequence.DefaultSequenceGenerator
		// sequenceGenerator =
		// new net.jcreate.e3.commons.id.sequence.DefaultSequenceGenerator();
		// sequenceGenerator.setMinValue(1000);
		// sequenceGenerator.setMaxValue(9999);
		// generator.setSequenceGenerator(sequenceGenerator);
		// for(int i = 0; i<100; i++ ){
		// System.out.println(generator.create());
		// }

		// while( true ){
		// System.out.println(sequenceGenerator.next());
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

}
