package com.cultivation_mod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class FengShuiCalc {
    private static int calcRadius = 16;

    public static int calculateFengShui(World world, BlockPos pos){
        int fengshuiScore = 0;
        for (int x = pos.getX() - calcRadius; x < pos.getX() + calcRadius +1; x++) {
            for (int z = pos.getX() - calcRadius; z < pos.getZ() + calcRadius +1; z++) {
                for (int y = pos.getY() - 1; y < pos.getY() + calcRadius +1; y++) {
                    BlockState block = world.getBlockState(new BlockPos(x, y, z));
                    if(block.isAir()){
                        continue;
                    }
                    String directionArea ="";

                    if(z>pos.getZ()){ // +Z, South
                        if(z-pos.getZ() > calcRadius/3){
                            directionArea +="S";
                        }
                    }
                    else{ // -Z, North
                        if(pos.getZ()-z > calcRadius/3){
                            directionArea +="N";
                        }
                    }
                    if(x>pos.getX()){ // +X, East
                        if(x-pos.getX() > calcRadius/3){
                            directionArea +="E";
                        }
                    }
                    else{ // -X, West
                        if(pos.getX()-x > calcRadius/3){
                            directionArea +="W";
                        }
                    }

                    switch (directionArea){
                        case ("N") ->{  //black,dark blues, water, glass, mirrors, water stuff, door(entranceway)
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.DOORS,BlockTags.UNDERWATER_BONEMEALS,BlockTags.CORALS,BlockTags.ICE
                            );
                            List<String> nNames = List.of(
                                    "black","blue","glass","water","lily","prismarine"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                        case ("S") ->{  //red,orange,yellow, fire, firey stuff, candles
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.CAMPFIRES,BlockTags.FIRE,BlockTags.CANDLES,BlockTags.INFINIBURN_NETHER
                            );
                            List<String> nNames = List.of(
                                    "red","yellow","orange","fire","coal","blaze","furnace"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                        case ("E") ->{  //green, wood, vegetation
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.LOGS,BlockTags.LEAVES,BlockTags.FLOWERS,BlockTags.FLOWER_POTS,BlockTags.SAPLINGS,BlockTags.CAVE_VINES,BlockTags.CROPS
                            );
                            List<String> nNames = List.of(
                                    "green","lime","moss","bush","wood","grass"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                        case ("W") ->{  //white, lightning, metal stuff?, "future stuff"?????
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.ANVIL
                            );
                            List<String> nNames = List.of(
                                    "white","light_gray","iron","copper","gold","steel","silver","lightning","clock","redstone","quartz"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                        case ("NE") ->{ //blue,green,black, earth, pottery, soil?, book stuff, "personal space"
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.TERRACOTTA,BlockTags.DIRT,BlockTags.SAND,BlockTags.FLOWER_POTS,BlockTags.BEDS,BlockTags.BASE_STONE_OVERWORLD
                            );
                            List<String> nNames = List.of(
                                    "green","cyan","black","light_blue","book","pot","lectern","sculk"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                        case ("NW") ->{ //white,grey,black, lightning, "travel stuff" ????, bells?
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.ANVIL
                            );
                            List<String> nNames = List.of(
                                    "white","gray","iron","copper","gold","steel","silver","lightning","redstone","clock","bell","totem","quartz"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                        case ("SE") ->{ //purple,blue,red,green, wood, vegetation, amethyst?, bamboo, art stuff?
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.LOGS,BlockTags.LEAVES,BlockTags.FLOWERS,BlockTags.FLOWER_POTS,BlockTags.SAPLINGS,BlockTags.CAVE_VINES,BlockTags.BAMBOO_BLOCKS,BlockTags.CROPS
                            );
                            List<String> nNames = List.of(
                                    "green","purple","blue","red","moss","bush","wood","grass","amethyst","pot"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                        case ("SW") ->{ //pink,red,white, earth, pottery, soil?, "pairs of things"??, love related?
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.TERRACOTTA,BlockTags.DIRT,BlockTags.SAND,BlockTags.FLOWER_POTS,BlockTags.BEDS,BlockTags.BASE_STONE_OVERWORLD
                            );
                            List<String> nNames = List.of(
                                    "pink","red","white","magenta","earth","pot","stone","honey","cherry","mangrove","purpur"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                        case ("") -> {  //yellow, brown, orange, earth, pottery, soil?, pretty open of space
                            List<TagKey<Block>> nTags = List.of(
                                    BlockTags.TERRACOTTA,BlockTags.DIRT,BlockTags.SAND,BlockTags.FLOWER_POTS,BlockTags.BEDS,BlockTags.BASE_STONE_OVERWORLD
                            );
                            List<String> nNames = List.of(
                                    "yellow","brown","orange","earth","pot","stone"
                            );
                            fengshuiScore += numMatchingTags(block,nTags);
                            fengshuiScore += numMatchingNames(block,nNames);
                        }
                    }


                }
            }
        }
        return fengshuiScore;
    }

    public static int numMatchingTags(BlockState block, List<TagKey<Block>> tags){
        int matchingTags = 0;
        for(TagKey<Block> tag:tags){
            if(block.isIn(tag))
                matchingTags++;
        }
        return matchingTags;
    }

    public static int numMatchingNames(BlockState block, List<String> names){
        int matchingNames = 0;
        for(String name:names){
            if(block.getRegistryEntry().getIdAsString().contains(name))
                matchingNames++;
        }
        return matchingNames;
    }


}
