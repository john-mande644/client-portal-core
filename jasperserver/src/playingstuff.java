import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.ScanOrder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/17/14
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class playingstuff {


    public static void main(String[] args){

        try{
            List<String> l = new ArrayList<String>();
            l.add("8589130");
            l.add("8589150");
            l.add("8589154");
            l.add("8589159");
            l.add("8589166");
            l.add("8589171");
            l.add("8589175");
            l.add("8583182");
            l.add("8583281");
            l.add("8583286");
            l.add("8583273");
            l.add("8583022");
            l.add("8583168");
            l.add("8583124");
            l.add("8583107");
            l.add("8619869");
            l.add("8589223");
            l.add("8589219");
            l.add("8620295");
            l.add("8565240");
            l.add("8575959");
            l.add("8571543");
            l.add("8565212");
            l.add("8564582");
            l.add("8565171");
            l.add("8565150");
            l.add("8575793");
            l.add("8576657");
            l.add("8576031");
            l.add("8575832");
            l.add("8575805");
            l.add("8571365");
            l.add("8565058");
            l.add("8576718");
            l.add("8582936");
            l.add("8576436");
            l.add("8583212");
            l.add("8571252");
            l.add("8565207");
            l.add("8575782");
            l.add("8575767");
            l.add("8575879");
            l.add("8589133");
            l.add("8583071");
            l.add("8583180");
            l.add("8583277");
            l.add("8565073");
            l.add("8571482");
            l.add("8583042");
            l.add("8576373");
            l.add("8564755");
            l.add("8565163");
            l.add("8571253");
            l.add("8576084");
            l.add("8576550");
            l.add("8583046");
            l.add("8576433");
            l.add("8565190");
            l.add("8564634");
            l.add("8571337");
            l.add("8565022");
            l.add("8564799");
            l.add("8576250");
            l.add("8576688");
            l.add("8576677");
            l.add("8571370");
            l.add("8571314");
            l.add("8565228");
            l.add("8565274");
            l.add("8571410");
            l.add("8571333");
            l.add("8571309");
            l.add("8575994");
            l.add("8582981");
            l.add("8575918");
            l.add("8571431");
            l.add("8583261");
            l.add("8575962");
            l.add("8583068");
            l.add("8576780");
            l.add("8576724");
            l.add("8565020");
            l.add("8575835");
            l.add("8576486");
            l.add("8576626");
            l.add("8583246");
            l.add("8576184");
            l.add("8576120");
            l.add("8575764");
            l.add("8575756");
            l.add("8582946");
            l.add("8576404");
            l.add("8619872");
            l.add("8576391");
            l.add("8576216");
            l.add("8589185");
            l.add("8583051");
            l.add("8589112");
            l.add("8571438");
            l.add("8575904");
            l.add("8565106");
            l.add("8565198");
            l.add("8565189");
            l.add("8565166");
            l.add("8571562");
            l.add("8571589");
            l.add("8576175");
            l.add("8620252");
            l.add("8583054");
            l.add("8589148");
            l.add("8582937");
            l.add("8576781");
            l.add("8576537");
            l.add("8576417");
            l.add("8576159");
            l.add("8576122");
            l.add("8575965");
            l.add("8576005");
            l.add("8571591");
            l.add("8571541");
            l.add("8565129");
            l.add("8565253");
            l.add("8571339");
            l.add("8565116");
            l.add("8565054");
            l.add("8563335");
            l.add("8571434");
            l.add("8576597");
            l.add("8576659");
            l.add("8571388");
            l.add("8565232");
            l.add("8575807");
            l.add("8575987");
            l.add("8576789");
            l.add("8576359");
            l.add("8576361");
            l.add("8571454");
            l.add("8565192");
            l.add("8576382");
            l.add("8576689");
            l.add("8571513");
            l.add("8576042");
            l.add("8571409");
            l.add("8564646");
            l.add("8564557");
            l.add("8564218");
            l.add("8564368");
            l.add("8565074");
            l.add("8576733");
            l.add("8564508");
            l.add("8562946");
            l.add("8564280");
            l.add("8571310");
            l.add("8564798");
            l.add("8576230");
            l.add("8576259");
            l.add("8576239");
            l.add("8576617");
            l.add("8571503");
            l.add("8571532");
            l.add("8564819");
            l.add("8576596");
            l.add("8576408");
            l.add("8575857");
            l.add("8576197");
            l.add("8571466");
            l.add("8564769");
            l.add("8575863");
            l.add("8571441");
            l.add("8576124");
            l.add("8575998");
            l.add("8565046");
            l.add("8564306");
            l.add("8564263");
            l.add("8564543");
            l.add("8563357");
            l.add("8563710");
            l.add("8564082");
            l.add("8563916");
            l.add("8563922");
            l.add("8563778");
            l.add("8563647");
            l.add("8563678");
            l.add("8571304");
            l.add("8564625");
            l.add("8564113");
            l.add("8571424");
            l.add("8563976");
            l.add("8564507");
            l.add("8563453");
            l.add("8564339");
            l.add("8563528");
            l.add("8564184");
            l.add("8564055");
            l.add("8565234");
            l.add("8565051");
            l.add("8576023");
            l.add("8563913");
            l.add("8565247");
            l.add("8565098");
            l.add("8564742");
            l.add("8564820");
            l.add("8564594");
            l.add("8562543");
            l.add("8565082");
            l.add("8575910");
            l.add("8576047");
            l.add("8575940");
            l.add("8565269");
            l.add("8565182");
            l.add("8565132");
            l.add("8565113");
            l.add("8565271");
            l.add("8565268");
            l.add("8571483");
            l.add("8571323");
            l.add("8583256");
            l.add("8565044");
            l.add("8564389");
            l.add("8564541");
            l.add("8575868");
            l.add("8565128");
            l.add("8565181");
            l.add("8564754");
            l.add("8564751");
            l.add("8571537");
            l.add("8576095");
            l.add("8564442");
            l.add("8576710");
            l.add("8576726");
            l.add("8582942");
            l.add("8576429");
            l.add("8576229");
            l.add("8576221");
            l.add("8576307");
            l.add("8583282");
            l.add("8583247");
            l.add("8619864");
            l.add("8564566");
            l.add("8575838");
            l.add("8565077");
            l.add("8565270");
            l.add("8576190");
            l.add("8571507");
            l.add("8565184");
            l.add("8589205");
            l.add("8583329");
            l.add("8583184");
            l.add("8583120");
            l.add("8576289");
            l.add("8583142");
            l.add("8583209");
            l.add("8583039");
            l.add("8565083");
            l.add("8589131");
            l.add("8583079");
            l.add("8582999");
            l.add("8583116");
            l.add("8583213");
            l.add("8575779");
            l.add("8576093");
            l.add("8576403");
            l.add("8576106");
            l.add("8564473");
            l.add("8564630");
            l.add("8576034");
            l.add("8575830");
            l.add("8564775");
            l.add("8571397");
            l.add("8575883");
            l.add("8576003");
            l.add("8576434");
            l.add("8576446");
            l.add("8576287");
            l.add("8576385");
            l.add("8576609");
            l.add("8576507");
            l.add("8583192");
            l.add("8583237");
            l.add("8589165");
            l.add("8589183");
            l.add("8582954");
            l.add("8571570");
            l.add("8583106");
            l.add("8582969");
            l.add("8575844");
            l.add("8571416");
            l.add("8583157");
            l.add("8583087");
            l.add("8565282");
            l.add("8582939");
            l.add("8576668");
            l.add("8576497");
            l.add("8576488");
            l.add("8576129");
            l.add("8565235");
            l.add("8565039");
            l.add("8576346");
            l.add("8583156");
            l.add("8564670");
            l.add("8564678");
            l.add("8564238");
            l.add("8564580");
            l.add("8565238");
            l.add("8583269");
            l.add("8576635");
            l.add("8576621");
            l.add("8619840");
            l.add("8565088");
            l.add("8565133");
            l.add("8576154");
            l.add("8575775");
            l.add("8576353");
            l.add("8583238");
            l.add("8583219");
            l.add("8583333");
            l.add("8583285");
            l.add("8589116");
            l.add("8583091");
            l.add("8619879");
            l.add("8619849");
            l.add("8620263");
            l.add("8564636");
            l.add("8565201");
            l.add("8565079");
            l.add("8564813");
            l.add("8564577");
            l.add("8564381");
            l.add("8564352");
            l.add("8589143");
            l.add("8576370");
            l.add("8564468");
            l.add("8564612");
            l.add("8565055");
            l.add("8571509");
            l.add("8565194");
            l.add("8576567");
            l.add("8571347");
            l.add("8571553");
            l.add("8576111");
            l.add("8575812");
            l.add("8589161");
            l.add("8565188");
            l.add("8564574");
            l.add("8575762");
            l.add("8564450");
            l.add("8583069");
            l.add("8571318");
            l.add("8564593");
            l.add("8571330");
            l.add("8571485");
            l.add("8576192");
            l.add("8583300");
            l.add("8583251");
            l.add("8576409");
            l.add("8576265");
            l.add("8589180");
            l.add("8589218");
            l.add("8571590");
            l.add("8575858");
            l.add("8565255");
            l.add("8564724");
            l.add("8575892");
            l.add("8576413");
            l.add("8571359");
            l.add("8564565");
            l.add("8575966");
            l.add("8575950");
            l.add("8576149");
            l.add("8576315");
            l.add("8583206");
            l.add("8583049");
            l.add("8582959");
            l.add("8589211");
            l.add("8620260");
            l.add("8583236");
            l.add("8589167");
            l.add("8583065");
            l.add("8583080");
            l.add("8576039");
            l.add("8575827");
            l.add("8575759");
            l.add("8571374");
            l.add("8571281");
            l.add("8565249");
            l.add("8565162");
            l.add("8565152");
            l.add("8564766");
            l.add("8565104");
            l.add("8576097");
            l.add("8564597");
            l.add("8576764");
            l.add("8575849");
            l.add("8564760");
            l.add("8589105");
            l.add("8571478");
            l.add("8576438");
            l.add("8571275");
            l.add("8564608");
            l.add("8564715");
            l.add("8576189");
            l.add("8576306");
            l.add("8620237");
            l.add("8565279");
            l.add("8565283");
            l.add("8575917");
            l.add("8620239");
            l.add("8620307");
            l.add("8583003");
            l.add("8583024");
            l.add("8564771");
            l.add("8565246");
            l.add("8583040");
            l.add("8583284");
            l.add("8565089");
            l.add("8571354");
            l.add("8576673");
            l.add("8565053");
            l.add("8571411");
            l.add("8571576");
            l.add("8564493");
            l.add("8564517");
            l.add("8575803");
            l.add("8575862");
            l.add("8564717");
            l.add("8571518");
            l.add("8576623");
            l.add("8576606");
            l.add("8583130");
            l.add("8583330");
            l.add("8576790");
            l.add("8576254");
            l.add("8575943");
            l.add("8576157");
            l.add("8576479");
            l.add("8582945");
            l.add("8619868");
            l.add("8564827");
            l.add("8571394");
            l.add("8565218");
            l.add("8564439");
            l.add("8564485");
            l.add("8564435");
            l.add("8564354");
            l.add("8564340");
            l.add("8564371");
            l.add("8565219");
            l.add("8565135");
            l.add("8565125");
            l.add("8564779");
            l.add("8565027");
            l.add("8571480");
            l.add("8619875");
            l.add("8564394");
            l.add("8564534");
            l.add("8565094");
            l.add("8564720");
            l.add("8564730");
            l.add("8564753");
            l.add("8564816");
            l.add("8565015");
            l.add("8565236");
            l.add("8571260");
            l.add("8571324");
            l.add("8571458");
            l.add("8576138");
            l.add("8575909");
            l.add("8564520");
            l.add("8564547");
            l.add("8564601");
            l.add("8564578");
            l.add("8564677");
            l.add("8564656");
            l.add("8564376");
            l.add("8564367");
            l.add("8564390");
            l.add("8583145");
            l.add("8583092");
            l.add("8583109");
            l.add("8583066");
            l.add("8583058");
            l.add("8589199");
            l.add("8589197");
            l.add("8583227");
            l.add("8576412");
            l.add("8576235");
            l.add("8582941");
            l.add("8576419");
            l.add("8583170");
            l.add("8583265");
            l.add("8576625");
            l.add("8564347");
            l.add("8576103");
            l.add("8564675");
            l.add("8564553");
            l.add("8575819");
            l.add("8576296");
            l.add("8576396");
            l.add("8576656");
            l.add("8589174");
            l.add("8583172");
            l.add("8582951");
            l.add("8565005");
            l.add("8575798");
            l.add("8571551");
            l.add("8575934");
            l.add("8564667");
            l.add("8583148");
            l.add("8583045");
            l.add("8583176");
            l.add("8571358");
            l.add("8571363");
            l.add("8571372");
            l.add("8571383");
            l.add("8571329");
            l.add("8565153");
            l.add("8565156");
            l.add("8564817");
            l.add("8564804");
            l.add("8564800");
            l.add("8564812");
            l.add("8564752");
            l.add("8564784");
            l.add("8564731");
            l.add("8564702");
            l.add("8576014");
            l.add("8576108");
            l.add("8576110");
            l.add("8571533");
            l.add("8571474");
            l.add("8571446");
            l.add("8564650");
            l.add("8564637");
            l.add("8589155");
            l.add("8582985");
            l.add("8576675");
            l.add("8576457");
            l.add("8576437");
            l.add("8576332");
            l.add("8576204");
            l.add("8620206");
            l.add("8619867");
            l.add("8619863");
            l.add("8576493");
            l.add("8565050");
            l.add("8571348");
            l.add("8576193");
            l.add("8576355");
            l.add("8620222");
            l.add("8583030");
            l.add("8571449");
            l.add("8571524");
            l.add("8575979");
            l.add("8582940");
            l.add("8589202");
            l.add("8576536");
            l.add("8583198");
            l.add("8576478");
            l.add("8589206");
            l.add("8583334");
            l.add("8583019");
            l.add("8575876");
            l.add("8564598");
            l.add("8576411");
            l.add("8576496");
            l.add("8576540");
            l.add("8583086");
            l.add("8583113");
            l.add("8589125");
            l.add("8564434");
            l.add("8575776");
            l.add("8571593");
            l.add("8565199");
            l.add("8565200");
            l.add("8565241");
            l.add("8571399");
            l.add("8565084");
            l.add("8564823");
            l.add("8571471");
            l.add("8571499");
            l.add("8589194");
            l.add("8576697");
            l.add("8564519");
            l.add("8571267");
            l.add("8565028");
            l.add("8571502");
            l.add("8575964");
            l.add("8576021");
            l.add("8576077");
            l.add("8576073");
            l.add("8576044");
            l.add("8571278");
            l.add("8576664");
            l.add("8576538");
            l.add("8576467");
            l.add("8576468");
            l.add("8576312");
            l.add("8583202");
            l.add("8583274");
            l.add("8583232");
            l.add("8583104");
            l.add("8583105");
            l.add("8582955");
            l.add("8582975");
            l.add("8564289");
            l.add("8576575");
            l.add("8576316");
            l.add("8583322");
            l.add("8576366");
            l.add("8571312");
            l.add("8576178");
            l.add("8576349");
            l.add("8576456");
            l.add("8576462");
            l.add("8576494");
            l.add("8576611");
            l.add("8589207");
            l.add("8583264");
            l.add("8583014");
            l.add("8576742");
            l.add("8576017");
            l.add("8576174");
            l.add("8576637");
            l.add("8620224");
            l.add("8583011");
            l.add("8576292");
            l.add("8583339");
            l.add("8583299");
            l.add("8583262");
            l.add("8583245");
            l.add("8583100");
            l.add("8583050");
            l.add("8576310");
            l.add("8576484");
            l.add("8576461");
            l.add("8576641");
            l.add("8576549");
            l.add("8619837");
            l.add("8575955");
            l.add("8575769");
            l.add("8565277");
            l.add("8565217");
            l.add("8565042");
            l.add("8564721");
            l.add("8571523");
            l.add("8576560");
            l.add("8576508");
            l.add("8576455");
            l.add("8583122");
            l.add("8576460");
            l.add("8576578");
            l.add("8576684");
            l.add("8564818");
            l.add("8576598");
            l.add("8589146");
            l.add("8576729");
            l.add("8589153");
            l.add("8583178");
            l.add("8583098");
            l.add("8582988");
            l.add("8565097");
            l.add("8575784");
            l.add("8576777");
            l.add("8619828");
            l.add("8576503");
            l.add("8576634");
            l.add("8576763");
            l.add("8582944");
            l.add("8583189");
            l.add("8575766");
            l.add("8565248");
            l.add("8565041");
            l.add("8583211");
            l.add("8583187");
            l.add("8589188");
            l.add("8571462");
            l.add("8575880");
            l.add("8589177");
            l.add("8576696");
            l.add("8575799");
            l.add("8571385");
            l.add("8575869");
            l.add("8575808");
            l.add("8571494");
            l.add("8571430");
            l.add("8576666");
            l.add("8576652");
            l.add("8576583");
            l.add("8576481");
            l.add("8576448");
            l.add("8576334");
            l.add("8576253");
            l.add("8583217");
            l.add("8582947");
            l.add("8582950");
            l.add("8583129");
            l.add("8576351");
            l.add("8571486");
            l.add("8575855");
            l.add("8564728");
            l.add("8575802");
            l.add("8583140");
            l.add("8583200");
            l.add("8583278");
            l.add("8576082");
            l.add("8583138");
            l.add("8582968");
            l.add("8576716");
            l.add("8576758");
            l.add("8576683");
            l.add("8583057");
            l.add("8589168");
            l.add("8620246");
            l.add("8575897");
            l.add("8565117");
            l.add("8619851");
            l.add("8589141");
            l.add("8583235");
            l.add("8583210");
            l.add("8583323");
            l.add("8583169");
            l.add("8565025");
            l.add("8565263");
            l.add("8576071");
            l.add("8575809");
            l.add("8575814");
            l.add("8583335");
            l.add("8583253");
            l.add("8582962");
            l.add("8589221");
            l.add("8571522");
            l.add("8564561");
            l.add("8575937");
            l.add("8575961");
            l.add("8575837");
            l.add("8575822");
            l.add("8575817");
            l.add("8575797");
            l.add("8575758");
            l.add("8571554");
            l.add("8575968");
            l.add("8575975");
            l.add("8576016");
            l.add("8576050");
            l.add("8576116");
            l.add("8565276");
            l.add("8565169");
            l.add("8565034");
            l.add("8565100");
            l.add("8565092");
            l.add("8565120");
            l.add("8564599");
            l.add("8564669");
            l.add("8619833");
            l.add("8620282");
            l.add("8583114");
            l.add("8583026");
            l.add("8583257");
            l.add("8583276");
            l.add("8583292");
            l.add("8583199");
            l.add("8583175");
            l.add("8583325");
            l.add("8583305");
            l.add("8589109");
            l.add("8589114");
            l.add("8589162");
            l.add("8576714");
            l.add("8576774");
            l.add("8576504");
            l.add("8576558");
            l.add("8576374");
            l.add("8576395");
            l.add("8576490");
            l.add("8576060");
            l.add("8576618");
            l.add("8576627");
            l.add("8576577");
            l.add("8576674");
            l.add("8576214");
            l.add("8583328");
            l.add("8583255");
            l.add("8582978");
            l.add("8619845");
            l.add("8619846");
            l.add("8620254");
            l.add("8575944");
            l.add("8571493");
            l.add("8575829");
            l.add("8565191");
            l.add("8582983");
            l.add("8576206");
            l.add("8576228");
            l.add("8576337");
            l.add("8576471");
            l.add("8583025");
            l.add("8564587");
            l.add("8576360");
            l.add("8576320");
            l.add("8576345");
            l.add("8576242");
            l.add("8583076");
            l.add("8583097");
            l.add("8582971");
            l.add("8582948");
            l.add("8582960");
            l.add("8583240");
            l.add("8583195");
            l.add("8583317");
            l.add("8583320");
            l.add("8620232");
            l.add("8564649");
            l.add("8564802");
            l.add("8564733");
            l.add("8565065");
            l.add("8575922");
            l.add("8575912");
            l.add("8576089");
            l.add("8564295");
            l.add("8564487");
            l.add("8619880");
            l.add("8583307");
            l.add("8583337");
            l.add("8589102");
            l.add("8583207");
            l.add("8583190");
            l.add("8583101");
            l.add("8583078");
            l.add("8576331");
            l.add("8576738");
            l.add("8576305");
            l.add("8589134");
            l.add("8575780");
            l.add("8575951");
            l.add("8571351");
            l.add("8571341");
            l.add("8589176");
            l.add("8583331");
            l.add("8583279");
            l.add("8576304");
            l.add("8576424");
            l.add("8576449");
            l.add("8576776");
            l.add("8576551");
            l.add("8619877");
            l.add("8575976");
            l.add("8575890");
            l.add("8575853");
            l.add("8576526");
            l.add("8576363");
            l.add("8589137");
            l.add("8582972");
            l.add("8564418");
            l.add("8575860");
            l.add("8571404");
            l.add("8582998");
            l.add("8576329");
            l.add("8576737");
            l.add("8576152");
            l.add("8576144");
            l.add("8576169");
            l.add("8576051");
            l.add("8576048");
            l.add("8575888");
            l.add("8575969");
            l.add("8571489");
            l.add("8575796");
            l.add("8576759");
            l.add("8576762");
            l.add("8576667");
            l.add("8576719");
            l.add("8576642");
            l.add("8576639");
            l.add("8576286");
            l.add("8576243");
            l.add("8576249");
            l.add("8576252");
            l.add("8576231");
            l.add("8576365");
            l.add("8576358");
            l.add("8576500");
            l.add("8576487");
            l.add("8583007");
            l.add("8583084");
            l.add("8583018");
            l.add("8583111");
            l.add("8589144");
            l.add("8620235");
            l.add("8619824");
            l.add("8620275");
            l.add("8620309");
            l.add("8576519");
            l.add("8576613");
            l.add("8576007");
            l.add("8575929");
            l.add("8575927");
            l.add("8576058");
            l.add("8576114");
            l.add("8576115");
            l.add("8576145");
            l.add("8575800");
            l.add("8575795");
            l.add("8575763");
            l.add("8571378");
            l.add("8576636");
            l.add("8576571");
            l.add("8576709");
            l.add("8576705");
            l.add("8576698");
            l.add("8576739");
            l.add("8576207");
            l.add("8576244");
            l.add("8576240");
            l.add("8589179");
            l.add("8589192");
            l.add("8583288");
            l.add("8583020");
            l.add("8583082");
            l.add("8583077");
            l.add("8619855");
            l.add("8582956");
            l.add("8576318");
            l.add("8576223");
            l.add("8576498");
            l.add("8576680");
            l.add("8576646");
            l.add("8576607");
            l.add("8583163");
            l.add("8583270");
            l.add("8583326");
            l.add("8589103");
            l.add("8589106");
            l.add("8589107");
            l.add("8565114");
            l.add("8575823");
            l.add("8576163");
            l.add("8576062");
            l.add("8564440");



                for (String s:l){
                    getPDFFile(s);
                }



        } catch (Exception e){
            e.printStackTrace();

        }


    }


    private static void getPDFFile(String orderID){
        try{
            System.out.println("Working on order ID "+ orderID);
            //get the order
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load((OwdOrder.class),Integer.parseInt(orderID));
            //get the scans

            Collection scans = order.getScanDocs();
            if(scans !=null){
                Iterator it2 = scans.iterator();
                if(it2.hasNext()){
                    ScanOrder so = (ScanOrder) it2.next();
                    System.out.println("getting file" + so.getName());
                    byte[] fileBytes = ScanManager.getScanFromOwdOrder(order, so.getName());
                    System.out.println("writting file");
                    FileUtils.writeByteArrayToFile(new File("c:\\packingslips\\" + so.getName().replaceAll(":", "-")), fileBytes);

                    //  FileOutputStream fos = new FileOutputStream(new File("c:\\packingslips\\"+so.getName()));
                    System.out.println("created stream");
                    //  fos.write(fileBytes);
                    System.out.println("wroted it");
                    //  fos.close();

                    //updateFileDownloaded(printID,temp);

                }


            }else{

                throw new Exception("No scans were found");
            }


        } catch(Exception e){
            e.printStackTrace();
            //log.error("There was an error getting the PDF",e);
        }
    }
}