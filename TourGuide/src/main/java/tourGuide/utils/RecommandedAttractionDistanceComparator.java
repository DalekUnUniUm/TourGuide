package tourGuide.utils;

import org.apache.commons.lang3.builder.CompareToBuilder;
import tourGuide.user.RecommandedAttraction;

import java.util.Comparator;

public class RecommandedAttractionDistanceComparator implements Comparator<RecommandedAttraction> {

    @Override
    public int compare(RecommandedAttraction rA1, RecommandedAttraction rA2){
        return new CompareToBuilder()
                .append(rA1.getDistance(),rA2.getDistance()).toComparison();
    }

}
