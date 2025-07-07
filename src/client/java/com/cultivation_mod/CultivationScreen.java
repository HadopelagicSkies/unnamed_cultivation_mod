package com.cultivation_mod;

import com.cultivation_mod.cultivation_setup.PlayerCultivationAttachments;
import com.cultivation_mod.element_setup.AxisElements;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CultivationScreen extends Screen {
    private final PlayerEntity player;
    private final int playerTier;
    private final int titleX;
    private final int titleY;
    private final int backgroundWidth;
    private final int backgroundHeight;
    private float mouseX;
    private float mouseY;
    private int ticksOpen;
    private long startTime;
    private int statsX = 180;
    private int statsY = 16;

    private static final int routingButtonSize=15;
    private static final int pentagonX=220;
    private static final int qiMeterHeight = 148;

    private static final Identifier CW_BUTTON_ICON = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/cw_button_icon.png");
    private static final Identifier CCW_BUTTON_ICON = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/ccw_button_icon.png");
    private static final Identifier QM_BUTTON_ICON = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/qm_button_icon.png");

    private static final Identifier BACKGROUND_TEXTURE = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/cultivation_screen.png");
    private static final Identifier MERIDIANS = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/meridians.png");
    private static final Identifier DANTIAN = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/dantian.png");

    private static final Identifier ARML1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/arml1.png");
    private static final Identifier ARML2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/arml2.png");
    private static final Identifier ARML3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/arml3.png");
    private static final Identifier ARML_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/arml_dark.png");

    private static final Identifier ARMR1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/armr1.png");
    private static final Identifier ARMR2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/armr2.png");
    private static final Identifier ARMR3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/armr3.png");
    private static final Identifier ARMR_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/armr_dark.png");

    private static final Identifier SHOULDERL1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/shoulderl1.png");
    private static final Identifier SHOULDERL2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/shoulderl2.png");
    private static final Identifier SHOULDERL3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/shoulderl3.png");
    private static final Identifier SHOULDERL_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/shoulderl_dark.png");

    private static final Identifier SHOULDERR1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/shoulderr1.png");
    private static final Identifier SHOULDERR2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/shoulderr2.png");
    private static final Identifier SHOULDERR3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/shoulderr3.png");
    private static final Identifier SHOULDERR_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/shoulderr_dark.png");

    private static final Identifier LEGL1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/legl1.png");
    private static final Identifier LEGL2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/legl2.png");
    private static final Identifier LEGL3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/legl3.png");
    private static final Identifier LEGL_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/legl_dark.png");

    private static final Identifier LEGR1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/legr1.png");
    private static final Identifier LEGR2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/legr2.png");
    private static final Identifier LEGR3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/legr3.png");
    private static final Identifier LEGR_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/legr_dark.png");

    private static final Identifier HEAD1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/head1.png");
    private static final Identifier HEAD2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/head2.png");
    private static final Identifier HEAD3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/head3.png");
    private static final Identifier HEAD_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/head_dark.png");

    private static final Identifier HEART1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/heart1.png");
    private static final Identifier HEART2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/heart2.png");
    private static final Identifier HEART3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/heart3.png");
    private static final Identifier HEART_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/heart_dark.png");

    private static final Identifier STOMACH1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/stomach1.png");
    private static final Identifier STOMACH2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/stomach2.png");
    private static final Identifier STOMACH3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/stomach3.png");
    private static final Identifier STOMACH_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/stomach_dark.png");

    private static final Identifier GUT1 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/gut1.png");
    private static final Identifier GUT2 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/gut2.png");
    private static final Identifier GUT3 = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/gut3.png");
    private static final Identifier GUT_DARK = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/gut_dark.png");

    private static final Identifier PENTAGON_BACKGROUND = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/pentagon_background.png");
    private static final Identifier PENTAGON_FIRE = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/pentagon_fire.png");
    private static final Identifier PENTAGON_WATER = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/pentagon_water.png");
    private static final Identifier PENTAGON_AIR = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/pentagon_air.png");
    private static final Identifier PENTAGON_EARTH = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/pentagon_earth.png");
    private static final Identifier PENTAGON_LIGHTNING = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/pentagon_lightning.png");
    private static final Identifier PENTAGON_MIDDLE = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/pentagon_middle.png");

    private static final Identifier QI_METER_BACKGROUND = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/qi_meter_background.png");
    private static final Identifier QI_METER = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen/qi_meter.png");



    private AcceptCultivationButton acceptCultivationButton;
    private List<RotationDirectionButton> rotationDirectionButtons = new ArrayList<>();

    private Map<String, Integer> meridianDirections = new HashMap<>();
    private List<String> meridianPathTracker = new ArrayList<>(List.of("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"));

    protected CultivationScreen(PlayerEntity player) {
        super(Text.translatable("gui.cultivation_mod.cultivation_screen"));
        this.player = player;
        this.playerTier = PlayerCultivationAttachments.getRealm(player);
        this.titleX = 225;
        this.titleY = 7;
        this.backgroundWidth = 332;
        this.backgroundHeight = 201;
        this.startTime = MinecraftClient.getInstance().world.getTime();
        PlayerCultivationAttachments.getMeridianProgress(this.player).forEach((meridian, progress) ->{
            meridianDirections.put(meridian,0);
        });
        meridianDirections.put("dantian",0);
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) /2;
        int y = (height - backgroundHeight) /2;
        context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawBackground(context,delta,mouseX,mouseY);

        this.mouseX = (float)mouseX;
        this.mouseY = (float)mouseY;
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawText(textRenderer,this.title,i + titleX, j + titleY, Colors.BLACK,false);


        context.drawTexture(RenderLayer::getGuiTextured, PENTAGON_BACKGROUND, i + pentagonX, j + backgroundHeight - 15 -54, 0.0F, 0.0F, 54, 54, 54, 54);
        if(playerTier >= 0) {
            //shifting around method coords in here rather than changing actual ones further down after changing the background layout
            //x -59, y +2
            context.drawTexture(RenderLayer::getGuiTextured, MERIDIANS, i + 34, j + 29, 0.0F, 0.0F, 91, 113, 91, 113, ColorHelper.getArgb(0, 104, 178));
            drawMeridianDirection(context,i+1-59,j+1+2); // coords were based on the dark texture coords, which are 1 larger each direction
            drawMeridianClearing(context,i-59,j+2);
            context.drawTexture(RenderLayer::getGuiTextured, DANTIAN, i + 71, j + 94, 0.0F, 0.0F, 17, 17, 17, 17);
            drawPentagonTextures(context,i,j);
            drawPentagonText(context,i,j);
            drawStatsText(context,i,j);
        }
        else{

        }
        drawButtonIcons(context);
        context.drawTexture(RenderLayer::getGuiTextured, QI_METER_BACKGROUND, i+157, j+10, 0.0F, 0.0F, 18, 157, 18, 157);
        context.drawTexture(RenderLayer::getGuiTextured, QI_METER, i+162, j+15+qiMeterHeight-(qiMeterHeight*PlayerCultivationAttachments.getQi(player)/ProcessCultivation.getRealmMaxQi(playerTier)), 0.0F, 0.0F, 8,(qiMeterHeight*PlayerCultivationAttachments.getQi(player)/ProcessCultivation.getRealmMaxQi(playerTier)) , 8, 148);

        if(acceptCultivationButton.running&&ticksOpen!=0) {
            acceptCultivationButton.setMessage(Text.translatable(CultivationMod.MOD_ID+".cultivation_screen.running",
                    ticksOpen /1200 + ":" + ((((ticksOpen/20)%60)<10) ? "0"+(ticksOpen/20)%60 : ""+(ticksOpen/20)%60)));
            if(ticksOpen%200 == 0){
                sendPacket();
            }
        }

        this.ticksOpen = (int) (MinecraftClient.getInstance().world.getTime() - this.startTime);
    }

    private void drawPentagonTextures(DrawContext context, int i, int j){

        Map<AxisElements, Integer> elementMap = PlayerElementAttachments.getElementMap(this.player);
        int totalElements = elementMap.values().stream().mapToInt(value -> value).sum() - elementMap.get(AxisElements.YIN) - elementMap.get(AxisElements.YANG);
        elementMap.forEach(((elements, integer) -> {
            switch (elements){
                case FIRE -> context.drawTexture(RenderLayer::getGuiTextured, PENTAGON_FIRE, i + pentagonX, j + backgroundHeight - 15 -54, 0.0F, 0.0F, 54, 54, 54, 54,ColorHelper.withAlpha(Math.max(255*integer/ totalElements,50),AxisElements.FIRE.getColor()));
                case WATER -> context.drawTexture(RenderLayer::getGuiTextured, PENTAGON_WATER, i + pentagonX, j + backgroundHeight - 15 -54, 0.0F, 0.0F, 54, 54, 54, 54,ColorHelper.withAlpha(Math.max(255*integer/ totalElements,50),AxisElements.WATER.getColor()));
                case AIR -> context.drawTexture(RenderLayer::getGuiTextured, PENTAGON_AIR, i + pentagonX, j + backgroundHeight - 15 -54, 0.0F, 0.0F, 54, 54, 54, 54,ColorHelper.withAlpha(Math.max(255*integer/ totalElements,50),AxisElements.AIR.getColor()));
                case EARTH -> context.drawTexture(RenderLayer::getGuiTextured, PENTAGON_EARTH, i + pentagonX, j + backgroundHeight - 15 -54, 0.0F, 0.0F, 54, 54, 54, 54,ColorHelper.withAlpha(Math.max(255*integer/ totalElements,50),AxisElements.EARTH.getColor()));
                case LIGHTNING -> context.drawTexture(RenderLayer::getGuiTextured, PENTAGON_LIGHTNING, i + pentagonX, j + backgroundHeight - 15 -54, 0.0F, 0.0F, 54, 54, 54, 54,ColorHelper.withAlpha(Math.max(255*integer/ totalElements,50),AxisElements.LIGHTNING.getColor()));
            }
        }));
        context.drawTexture(RenderLayer::getGuiTextured, PENTAGON_MIDDLE, i + pentagonX, j + backgroundHeight - 15 -54, 0.0F, 0.0F, 54, 54, 54, 54,
                elementMap.get(AxisElements.YIN) > elementMap.get(AxisElements.YANG)?ColorHelper.withAlpha((int)(255*0.75),AxisElements.YIN.getColor()):ColorHelper.withAlpha((int)(255*0.75),AxisElements.YANG.getColor()));
    }

    private void drawPentagonText(DrawContext context, int i, int j){

        context.drawText(textRenderer,Text.translatable("cultivation_mod.fire"),
                i + 238, j + 122, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.FIRE)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.FIRE));
        context.drawText(textRenderer,Text.translatable("cultivation_mod.water"),
                i + 197, j + 175, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.WATER)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.WATER));
        context.drawText(textRenderer,Text.translatable("cultivation_mod.air"),
                i + 204, j + 150, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.AIR)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.AIR));
        context.drawText(textRenderer,Text.translatable("cultivation_mod.earth"),
                i + 277, j + 150, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.EARTH)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.EARTH));
        context.drawText(textRenderer,Text.translatable("cultivation_mod.lightning"),
                i + 270, j + 175, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.LIGHTNING)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.LIGHTNING));
    }

    private void drawStatsText(DrawContext context, int i, int j){
        context.drawText(textRenderer,Text.translatable("cultivation_mod.cultivation_screen.realm",PlayerCultivationAttachments.getRealm(player)),
                i + statsX, j + statsY, Colors.BLACK,false);
        context.drawText(textRenderer,Text.translatable("cultivation_mod.cultivation_screen.refined_qi",PlayerCultivationAttachments.getQi(player)),
                i + statsX, j + statsY+15, Colors.BLACK,false);

        context.drawText(textRenderer,Text.translatable("cultivation_mod.yin").append(": "+
                        PlayerElementAttachments.getElementLevel(player,AxisElements.YIN)+
                        (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.YIN)?" ★":"")),
                i + statsX, j + statsY+30, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.YIN)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.YIN));
        context.drawText(textRenderer,Text.translatable("cultivation_mod.yang").append(": "+
                        PlayerElementAttachments.getElementLevel(player,AxisElements.YANG)+
                        (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.YANG)?" ★":"")),
                i + statsX+70, j + statsY+30, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.YANG)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.YANG));

        context.drawText(textRenderer,Text.translatable("cultivation_mod.fire").append(": "+
                        PlayerElementAttachments.getElementLevel(player,AxisElements.FIRE)+
                        (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.FIRE)?" ★":"")),
                i + statsX, j + statsY+45, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.FIRE)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.FIRE));
        context.drawText(textRenderer,Text.translatable("cultivation_mod.water").append(": "+
                        PlayerElementAttachments.getElementLevel(player,AxisElements.WATER)+
                        (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.WATER)?" ★":"")),
                i + statsX+70, j + statsY+45, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.WATER)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.WATER));

        context.drawText(textRenderer,Text.translatable("cultivation_mod.air").append(": "+
                        PlayerElementAttachments.getElementLevel(player,AxisElements.AIR)+
                        (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.AIR)?" ★":"")),
                i + statsX, j + statsY+60, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.AIR)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.AIR));
        context.drawText(textRenderer,Text.translatable("cultivation_mod.earth").append(": "+
                        PlayerElementAttachments.getElementLevel(player,AxisElements.EARTH)+
                        (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.EARTH)?" ★":"")),
                i + statsX+70, j + statsY+60, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.EARTH)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.EARTH));

        context.drawText(textRenderer,Text.translatable("cultivation_mod.lightning").append(": "+
                        PlayerElementAttachments.getElementLevel(player,AxisElements.LIGHTNING)+
                        (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.LIGHTNING)?" ★":"")),
                i + statsX, j + statsY+75, (PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.LIGHTNING)? Formatting.GOLD.getColorValue():Colors.BLACK),
                PlayerElementAttachments.getFavoredElements(player).contains(AxisElements.LIGHTNING));
    }

    private void drawButtonIcons(DrawContext context){
        for(RotationDirectionButton button:this.rotationDirectionButtons){
            int x = button.getX();
            int y = button.getY();

            if (button.visible) {
                context.getMatrices().push();
                context.getMatrices().translate(0,0,20);
                if(button.state == 1)
                    context.drawTexture(RenderLayer::getGuiTextured, CW_BUTTON_ICON, x, y, 0.0F, 0.0F, 15, 15, 15, 15);
                else if(button.state == 2)
                    context.drawTexture(RenderLayer::getGuiTextured, CCW_BUTTON_ICON, x, y, 0.0F, 0.0F, 15, 15, 15, 15);
                else
                    context.drawTexture(RenderLayer::getGuiTextured, QM_BUTTON_ICON, x, y, 0.0F, 0.0F, 15, 15, 15, 15);
                context.getMatrices().pop();
            }
        }
    }

    private void drawMeridianDirection(DrawContext context, int i, int j) {
        int frequency = 10;
        int frameTime = this.ticksOpen % frequency+1;

        meridianDirections.forEach((meridian,direction) -> {
            switch (meridian) {
                case ("head") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==1)
                                context.drawTexture(RenderLayer::getGuiTextured, HEAD1, i+123, j+26, 0.0F, 0.0F, 29, 29, 29, 29);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, HEAD3, i+123, j+26, 0.0F, 0.0F, 29, 29, 29, 29);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, HEAD2, i+123, j+26, 0.0F, 0.0F, 29, 29, 29, 29);
                        } else {
                            if(direction==1)
                                context.drawTexture(RenderLayer::getGuiTextured, HEAD3, i+123, j+26, 0.0F, 0.0F, 29, 29, 29, 29);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, HEAD1, i+123, j+26, 0.0F, 0.0F, 29, 29, 29, 29);
                        }
                    }
                }
                case ("heart") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, HEART1, i + 130, j + 54, 0.0F, 0.0F, 15, 23, 15, 23);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, HEART3, i + 130, j + 54, 0.0F, 0.0F, 15, 23, 15, 23);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, HEART2, i + 130, j + 54, 0.0F, 0.0F, 15, 23, 15, 23);
                        } else {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, HEART3, i + 130, j + 54, 0.0F, 0.0F, 15, 23, 15, 23);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, HEART1, i + 130, j + 54, 0.0F, 0.0F, 15, 23, 15, 23);
                        }
                    }
                }
                case ("stomach") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, STOMACH1, i+124, j+75, 0.0F, 0.0F, 27, 18, 27, 18);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, STOMACH3, i+124, j+75, 0.0F, 0.0F, 27, 18, 27, 18);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, STOMACH2, i+124, j+75, 0.0F, 0.0F, 27, 18, 27, 18);
                        } else {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, STOMACH3, i+124, j+75, 0.0F, 0.0F, 27, 18, 27, 18);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, STOMACH1, i+124, j+75, 0.0F, 0.0F, 27, 18, 27, 18);
                        }
                    }
                }
                case ("gut") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, GUT1, i+125, j+103, 0.0F, 0.0F, 25, 12, 25, 12);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, GUT3, i+125, j+103, 0.0F, 0.0F, 25, 12, 25, 12);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, GUT2, i+125, j+103, 0.0F, 0.0F, 25, 12, 25, 12);
                        } else {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, GUT3, i+125, j+103, 0.0F, 0.0F, 25, 12, 25, 12);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, GUT1, i+125, j+103, 0.0F, 0.0F, 25, 12, 25, 12);
                        }
                    }
                }
                case ("shoulderL") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERL1, i+111, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERL3, i+111, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, SHOULDERL2, i+111, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        } else {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERL3, i+111, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERL1, i+111, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        }
                    }
                }
                case ("shoulderR") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==1)
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR1, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR3, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR2, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        } else {
                            if(direction==1)
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR3, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR1, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        }
                    }
                }
                case ("armL") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==1)
                                context.drawTexture(RenderLayer::getGuiTextured, ARML1, i+92, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, ARML3, i+92, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, ARML2, i+92, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                        } else {
                            if(direction==1)
                                context.drawTexture(RenderLayer::getGuiTextured, ARML3, i+92, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, ARML1, i+92, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                        }
                    }
                }
                case ("armR") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, ARMR1, i+152, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, ARMR3, i+152, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, ARMR2, i+152, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                        } else {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, ARMR3, i+152, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, ARMR1, i+152, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                        }
                    }
                }
                case ("legL") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, LEGL1, i+104, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, LEGL3, i+104, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, LEGL2, i+104, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        } else {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, LEGL3, i+104, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, LEGL1, i+104, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        }
                    }
                }
                case ("legR") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==1)
                                context.drawTexture(RenderLayer::getGuiTextured, LEGR1, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, LEGR3, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, LEGR2, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        } else {
                            if(direction==1)
                                context.drawTexture(RenderLayer::getGuiTextured, LEGR3, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, LEGR1, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        }
                    }
                }
            }
        });
    }


    private void drawMeridianClearing(DrawContext context, int i, int j) {
        Map<String, Integer> meridianMap = PlayerCultivationAttachments.getMeridianProgress(this.player);
        meridianMap.forEach((meridian,progress) ->{
            switch (meridian){
                case("head") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, HEAD_DARK, i+122, j+25, 0.0F, 0.0F, 33, 33, 33, 33,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("heart") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, HEART_DARK, i+130, j+54, 0.0F, 0.0F, 17, 25, 17, 25,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("stomach") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, STOMACH_DARK, i+124, j+75, 0.0F, 0.0F, 29, 20, 29, 20,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("gut") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, GUT_DARK, i+125, j+103, 0.0F, 0.0F, 27, 14, 27, 14,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("shoulderL") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, SHOULDERL_DARK, i+111, j+61, 0.0F, 0.0F, 23, 16, 23, 16,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("shoulderR") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR_DARK, i+143, j+61, 0.0F, 0.0F, 23, 16, 23, 16,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("armL") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, ARML_DARK, i+92, j+69, 0.0F, 0.0F, 33, 35, 33, 35,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("armR") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, ARMR_DARK, i+152, j+69, 0.0F, 0.0F, 33, 35, 33, 35,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("legL") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, LEGL_DARK, i+104, j+109, 0.0F, 0.0F, 29, 32, 29, 32,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
                case("legR") -> {
                    context.drawTexture(RenderLayer::getGuiTextured, LEGR_DARK, i+144, j+109, 0.0F, 0.0F, 29, 32, 29, 32,
                            ColorHelper.getArgb(255 - (255*progress/100),255,255,255));
                }
            }
        });
    }

    private void sendPacket(){
        StringBuilder sendingString = new StringBuilder();
        for(String string:this.meridianPathTracker){
            sendingString.append(string);
        }
        CultivationMod.LOGGER.info(PlayerElementAttachments.getCultivationElements(player)+"");
        CultivationMod.LOGGER.info("Sending: " + sendingString);
        ClientPlayNetworking.send(new CultScreenPayload(sendingString.toString()));
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        int k = i + 79; //x center line
        int l = j + 102; // dantian y center

        this.acceptCultivationButton = this.addDrawableChild(new AcceptCultivationButton(i + 12, j+174, 11, (button) -> {
            if(((AcceptCultivationButton)button).running){
                ((AcceptCultivationButton)button).setRunning(false);
                rotationDirectionButtons.getFirst().visible = true;
                acceptCultivationButton.setMessage(Text.translatable(CultivationMod.MOD_ID+".cultivation_screen.not_running"));
            } else {
                rotationDirectionButtons.forEach((directionButton) -> directionButton.visible = false);
                ((AcceptCultivationButton) button).setRunning(true);
                this.startTime = MinecraftClient.getInstance().world.getTime();

            }

        }));

        //dantian
        rotationDirectionButtons.add(0,this.addDrawableChild(new RotationDirectionButton(k - (routingButtonSize/2), l - (routingButtonSize/2), 0, (button) -> {
            if (playerTier <0)
                return;
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if (newState == 3)
                newState = 0;
            ((RotationDirectionButton) button).setState(newState);

            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "stomach") >= 100) {
                rotationDirectionButtons.get(1).visible = true;
            } else {
                rotationDirectionButtons.get(1).visible = false;
            }
            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "gut") >= 100) {
                rotationDirectionButtons.get(8).visible = true;
            }else {
                rotationDirectionButtons.get(8).visible = false;
            }

            meridianDirections.put("dantian", newState == 2 ? -1 : newState);
            meridianPathTracker.set(0, ""+ newState);
        })));
        rotationDirectionButtons.get(0).visible = true;

        //stomach
        rotationDirectionButtons.add(1,this.addDrawableChild(new RotationDirectionButton(k - (routingButtonSize/2), l-15 - (routingButtonSize/2), 1, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((RotationDirectionButton) button).setState(newState);

            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "heart") >= 100) {
                rotationDirectionButtons.get(2).visible = true;
            } else{
                rotationDirectionButtons.get(2).visible = false;
            }
            meridianDirections.put("stomach",newState == 2 ? -1:newState);
            meridianPathTracker.set(1,""+newState);
        })));

        //heart
        rotationDirectionButtons.add(2,this.addDrawableChild(new RotationDirectionButton(k - (routingButtonSize/2), l-34 - (routingButtonSize/2), 2, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if (newState == 3)
                newState = 0;
            ((RotationDirectionButton) button).setState(newState);


            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "shoulderL") >= 100) {
                rotationDirectionButtons.get(3).visible = true;
            } else {
                rotationDirectionButtons.get(3).visible = false;
            }
            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "shoulderR") >= 100) {
                rotationDirectionButtons.get(4).visible = true;
            } else {
                rotationDirectionButtons.get(4).visible = false;
            }
            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "head") >= 100) {
                rotationDirectionButtons.get(7).visible = true;
            } else {
                rotationDirectionButtons.get(7).visible = false;
            }

            meridianDirections.put("heart", newState == 2 ? -1 : newState);
                meridianPathTracker.set(2, "" + newState);
        })));

        //shoulderL
        rotationDirectionButtons.add(3,this.addDrawableChild(new RotationDirectionButton(k - 20 - (routingButtonSize/2), l-38 - (routingButtonSize/2), 3, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if (newState == 3)
                newState = 0;
            ((RotationDirectionButton) button).setState(newState);

            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "armL") >= 100) {
                rotationDirectionButtons.get(5).visible = true;

            } else {
                rotationDirectionButtons.get(5).visible = false;
            }
            meridianDirections.put("shoulderL", newState == 2 ? -1 : newState);
            meridianPathTracker.set(3, "" + newState);
        })));

        //shoulderR
        rotationDirectionButtons.add(4,this.addDrawableChild(new RotationDirectionButton(k + 20 - (routingButtonSize/2), l-38 - (routingButtonSize/2), 4, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if (newState == 3)
                newState = 0;
            ((RotationDirectionButton) button).setState(newState);

            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "armR") >= 100) {
                rotationDirectionButtons.get(6).visible = true;

            } else {
                rotationDirectionButtons.get(6).visible = false;
            }
            meridianDirections.put("shoulderR", newState == 2 ? -1 : newState);
            meridianPathTracker.set(4, "" + newState);
        })));

        //armL
        rotationDirectionButtons.add(5,this.addDrawableChild(new RotationDirectionButton(k -49 - (routingButtonSize/2), l-15 - (routingButtonSize/2), 5, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((RotationDirectionButton) button).setState(newState);
            meridianDirections.put("armL",newState == 2 ? -1:newState);
            meridianPathTracker.set(5,""+newState);
        })));

        //armR
        rotationDirectionButtons.add(6,this.addDrawableChild(new RotationDirectionButton(k + 49 - (routingButtonSize/2), l-15 - (routingButtonSize/2), 6, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((RotationDirectionButton) button).setState(newState);
            meridianDirections.put("armR",newState == 2 ? -1:newState);
            meridianPathTracker.set(6,""+newState);
        })));

        //head
        rotationDirectionButtons.add(7,this.addDrawableChild(new RotationDirectionButton(k - (routingButtonSize/2), l-59 - (routingButtonSize/2), 7, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((RotationDirectionButton) button).setState(newState);
            meridianDirections.put("head",newState == 2 ? -1:newState);
            meridianPathTracker.set(7,""+newState);
        })));

        //gut
        rotationDirectionButtons.add(8,this.addDrawableChild(new RotationDirectionButton(k - (routingButtonSize/2), l+20 - (routingButtonSize/2), 8, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if (newState == 3)
                newState = 0;
            ((RotationDirectionButton) button).setState(newState);

            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "legL") >= 100) {
                rotationDirectionButtons.get(9).visible = true;
            } else {
                rotationDirectionButtons.get(9).visible = false;
            }
            if (PlayerCultivationAttachments.getSpecificMeridianProgress(player, "legR") >= 100) {
                rotationDirectionButtons.get(10).visible = true;
            } else {
                rotationDirectionButtons.get(10).visible = false;
            }

            meridianDirections.put("gut", newState == 2 ? -1 : newState);
            meridianPathTracker.set(8, "" + newState);
        })));

        //legL
        rotationDirectionButtons.add(9,this.addDrawableChild(new RotationDirectionButton(k -37 - (routingButtonSize/2), l+25 - (routingButtonSize/2), 9, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((RotationDirectionButton) button).setState(newState);
            meridianDirections.put("legL",newState == 2 ? -1:newState);
            meridianPathTracker.set(9,""+newState);
        })));

        //legR
        rotationDirectionButtons.add(10,this.addDrawableChild(new RotationDirectionButton(k +37- (routingButtonSize/2), l+25 - (routingButtonSize/2), 10, (button) -> {
            int oldState = ((RotationDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((RotationDirectionButton) button).setState(newState);
            meridianDirections.put("legR",newState == 2 ? -1:newState);
            meridianPathTracker.set(10,""+newState);
        })));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    public static class AcceptCultivationButton extends ButtonWidget {
        final int index;
        public boolean running = false;
        public AcceptCultivationButton(final int x, final int y, final int index, final PressAction onPress) {
            super(x, y, 135, 20, Text.translatable(CultivationMod.MOD_ID+".cultivation_screen.not_running"), onPress, DEFAULT_NARRATION_SUPPLIER);
            this.index = index;
            this.visible = true;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public boolean isRunning() {
            return running;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            context.getMatrices().push();
            context.getMatrices().translate(0,0,20);
            super.renderWidget(context, mouseX, mouseY, delta);
            context.getMatrices().pop();
        }


        public int getIndex() {
            return this.index;
        }
    }

    public static class RotationDirectionButton extends ButtonWidget {
        final int index;
        public int state = 0;

        public RotationDirectionButton(final int x, final int y, final int index, final PressAction onPress) {
            super(x, y,routingButtonSize,routingButtonSize, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
            this.index = index;
            this.visible = false;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getIndex() {
            return this.index;
        }
    }
}
