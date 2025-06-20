package com.cultivation_mod;

import com.cultivation_mod.cultivation_setup.PlayerCultivationAttatchments;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CultivationScreen extends Screen {
    private final PlayerEntity player;
    private final int titleX;
    private final int titleY;
    private final int backgroundWidth;
    private final int backgroundHeight;
    private float mouseX;
    private float mouseY;
    private int framesOpen;

    private static final int routingButtonSize=9;

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

    private AcceptCultivationButton acceptCultivationButton;
    private Map<String, IntersectionDirectionButton> intersectionDirectionButtons = new HashMap<>();
    private StartLocationButton[] startLocationButtons = new StartLocationButton[4];

    private Map<String, Integer> meridianDirections = new HashMap<>();
    private List<String> meridianPathTracker = new ArrayList<>();

    protected CultivationScreen(PlayerEntity player) {
        super(Text.translatable("gui.cultivation_mod.cultivation_screen"));
        this.player = player;
        this.titleX = 10;
        this.titleY = 10;
        this.backgroundWidth = 276;
        this.backgroundHeight = 253;
        PlayerCultivationAttatchments.getMeridianProgress(this.player).forEach((meridian,progress) ->{
            meridianDirections.put(meridian,0);
        });

    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) /2;
        int y = (height - backgroundHeight) /2;
        context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        drawBackground(context,delta,mouseX,mouseY);
        super.render(context, mouseX, mouseY, delta);

        this.mouseX = (float)mouseX;
        this.mouseY = (float)mouseY;
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawText(textRenderer,this.title,i + titleX, j + titleY, Colors.BLACK,false);
        context.drawTexture(RenderLayer::getGuiTextured, MERIDIANS, i+93, j+27, 0.0F, 0.0F, 91, 113, 91, 113,ColorHelper.getArgb(0,104,178));
        drawMeridianDirection(context);
        drawMeridianClearing(context);
        context.drawTexture(RenderLayer::getGuiTextured, DANTIAN, i+130, j+92, 0.0F, 0.0F, 17, 17, 17, 17);

        this.framesOpen++;
    }

    private void drawMeridianDirection(DrawContext context) {
        int i = ((this.width - this.backgroundWidth) / 2) +1;   // coords were based on the dark texture coords, which are 1 larger each direction
        int j = ((this.height - this.backgroundHeight) / 2) +1; // manually scooching over here
        int frequency = 30;
        int frameTime = this.framesOpen % frequency+1;

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
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR1, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR3, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR2, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        } else {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR3, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, SHOULDERR1, i+143, j+61, 0.0F, 0.0F, 20, 14, 20, 14);
                        }
                    }
                }
                case ("armL") -> {
                    if(direction!=0) {
                        if (frameTime <= frequency/3) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, ARML1, i+92, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, ARML3, i+92, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                        } else if (frameTime <= 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, ARML2, i+92, j+69, 0.0F, 0.0F, 31, 33, 31, 33);
                        } else {
                            if(direction==-1)
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
                                context.drawTexture(RenderLayer::getGuiTextured, LEGL3, i+104, j+109, 0.0F, 0.0F, 27, 30, 27, 305);
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
                        if (frameTime == frequency/3) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, LEGR1, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, LEGR3, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        } else if (frameTime == 2*frequency/3) {
                            context.drawTexture(RenderLayer::getGuiTextured, LEGR2, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        } else if (frameTime == frequency) {
                            if(direction==-1)
                                context.drawTexture(RenderLayer::getGuiTextured, LEGR3, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                            else
                                context.drawTexture(RenderLayer::getGuiTextured, LEGR1, i+144, j+109, 0.0F, 0.0F, 27, 30, 27, 30);
                        }
                    }
                }
            }
        });
    }


    private void drawMeridianClearing(DrawContext context) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        Map<String, Integer> meridianMap = PlayerCultivationAttatchments.getMeridianProgress(this.player);
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

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        int k = i + (this.backgroundWidth/2); //x center line
        int l = j+100; // dantian y center

        this.acceptCultivationButton = this.addDrawableChild(new AcceptCultivationButton(i + 70, j+169, 0, (button) -> {
            Map<String, Integer> meridianMap = PlayerCultivationAttatchments.getMeridianProgress(this.player);
            meridianMap.forEach((meridian,progress) ->{
                PlayerCultivationAttatchments.setSpecificMeridianProgress(this.player,meridian,100);
                CultivationMod.LOGGER.info(PlayerElementAttachments.getCultivationElements(player)+"");
            });
        }));

        startLocationButtons[0] = this.addDrawableChild(new StartLocationButton(k-4 - (routingButtonSize/2), l-4 - (routingButtonSize/2), 1, (button) -> {
            if(meridianPathTracker.size() == 1)
                meridianPathTracker.add(0,"B0");
            else
                meridianPathTracker.add("B0");

            startLocationButtons[1].visible = false;
            startLocationButtons[2].visible = false;
            startLocationButtons[3].visible = false;

            if(!meridianPathTracker.getLast().equals("G")){
                meridianDirections.forEach((key,value) -> meridianDirections.put(key,0) );
                intersectionDirectionButtons.forEach((key,intersectionButton) -> intersectionButton.visible = false);
            }

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"stomach") >= 100) {
                intersectionDirectionButtons.get("S").visible = true;
                meridianDirections.put("stomach", 1);
            }
        }));
        startLocationButtons[1] = this.addDrawableChild(new StartLocationButton(k+5 - (routingButtonSize/2), l-4 - (routingButtonSize/2), 2, (button) -> {
            if(meridianPathTracker.size() == 1)
                meridianPathTracker.add(0,"B1");
            else
                meridianPathTracker.add("B1");

            startLocationButtons[0].visible = false;
            startLocationButtons[2].visible = false;
            startLocationButtons[3].visible = false;

            if(!meridianPathTracker.getLast().equals("G")){
                meridianDirections.forEach((key,value) -> meridianDirections.put(key,0) );
                intersectionDirectionButtons.forEach((key,intersectionButton) -> intersectionButton.visible = false);
            }

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"stomach") >= 100) {
                intersectionDirectionButtons.get("S").visible = true;
                meridianDirections.put("stomach", -1);
            }
        }));
        startLocationButtons[2] = this.addDrawableChild(new StartLocationButton(k-4 - (routingButtonSize/2), l+5 - (routingButtonSize/2), 3, (button) -> {
            if(meridianPathTracker.size() == 1)
                meridianPathTracker.add(0,"B2");
            else
                meridianPathTracker.add("B2");

            startLocationButtons[0].visible = false;
            startLocationButtons[1].visible = false;
            startLocationButtons[3].visible = false;

            if(!meridianPathTracker.getLast().equals("S")){
                meridianDirections.forEach((key,value) -> meridianDirections.put(key,0) );
                intersectionDirectionButtons.forEach((key,intersectionButton) -> intersectionButton.visible = false);
            }

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"gut") >= 100) {
                //intersectionDirectionButtons.get("G").visible = true; // do corresponding gut intersection button
                meridianDirections.put("gut", -1);
            }
        }));
        startLocationButtons[3] = this.addDrawableChild(new StartLocationButton(k+5 - (routingButtonSize/2), l+5 - (routingButtonSize/2), 4, (button) -> {
            if(meridianPathTracker.size() == 1)
                meridianPathTracker.add(0,"B3");
            else
                meridianPathTracker.add("B3");

            startLocationButtons[0].visible = false;
            startLocationButtons[1].visible = false;
            startLocationButtons[2].visible = false;

            if(!meridianPathTracker.getLast().equals("s")){
                meridianDirections.forEach((key,value) -> meridianDirections.put(key,0) );
                intersectionDirectionButtons.forEach((key,intersectionButton) -> intersectionButton.visible = false);
            }

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"gut") >= 100) {
                //intersectionDirectionButtons.get("G").visible = true; // do corresponding gut intersection button
                meridianDirections.put("gut", 1);
            }
        }));

        // top of stomach
        intersectionDirectionButtons.put("S",this.addDrawableChild(new IntersectionDirectionButton(k - (routingButtonSize/2), l-25 - (routingButtonSize/2), 5, (button) -> {
            int oldState = ((IntersectionDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((IntersectionDirectionButton) button).setState(newState);

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"heart") < 100) {
                newState = 0;
            }

            if(newState==0){
                meridianDirections.put("heart",0);
                startLocationButtons[2].visible = true;
                startLocationButtons[3].visible = true;
                intersectionDirectionButtons.get("SHL").visible = false; // left side of heart
                intersectionDirectionButtons.get("SHR").visible = false; // left side of heart
            }
            else if(newState==1){
                meridianDirections.put("heart",1);
                startLocationButtons[2].visible = false;
                startLocationButtons[3].visible = false;
                intersectionDirectionButtons.get("SHL").visible = true; // left side of heart
                intersectionDirectionButtons.get("SHR").visible = false; // left side of heart
            }
            else{
                meridianDirections.put("heart",-1);
                startLocationButtons[2].visible = false;
                startLocationButtons[3].visible = false;
                intersectionDirectionButtons.get("SHR").visible = true; // right side of heart
                intersectionDirectionButtons.get("SHL").visible = false; // left side of heart
            }

            if(meridianPathTracker.getLast().startsWith("S"))
                meridianPathTracker.add(meridianPathTracker.size()-1,"S"+newState);
            else
                meridianPathTracker.add("S"+newState);
        })));

        // right side of heart
        intersectionDirectionButtons.put("SHR", this.addDrawableChild(new IntersectionDirectionButton(k - (routingButtonSize/2) + 7, l-32 - (routingButtonSize/2), 5, (button) -> {
            int oldState = ((IntersectionDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((IntersectionDirectionButton) button).setState(newState);

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"shoulderR") < 100) {
                newState = 0;
            }

            if(newState==0){
                meridianDirections.put("shoulderR",0);
                intersectionDirectionButtons.get("AR").visible = false;  // right arm
                intersectionDirectionButtons.get("HE").visible = true;  // top of heart
            }
            else if(newState==1){
                meridianDirections.put("shoulderR",1);
                intersectionDirectionButtons.get("AR").visible = true;   // right arm
                intersectionDirectionButtons.get("HE").visible = false;  // top of heart
            }
            else{
                meridianDirections.put("shoulderR",-1);
                intersectionDirectionButtons.get("AR").visible = true;   // right arm
                intersectionDirectionButtons.get("HE").visible = false;  // top of heart
            }

            startLocationButtons[0].visible = false;
            startLocationButtons[1].visible = false;
            startLocationButtons[2].visible = false;
            startLocationButtons[3].visible = false;

            if(meridianPathTracker.getLast().startsWith("SHR"))
                meridianPathTracker.add(meridianPathTracker.size()-1,"SHR"+newState);
            else
                meridianPathTracker.add("SHR"+newState);
        })));

        // right arm
        intersectionDirectionButtons.put("AR",this.addDrawableChild(new IntersectionDirectionButton(k - (routingButtonSize/2) + 25, l-28 - (routingButtonSize/2), 5, (button) -> {
            int oldState = ((IntersectionDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((IntersectionDirectionButton) button).setState(newState);

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"armR") < 100) {
                newState = 0;
            }

            if(newState==0){
                meridianDirections.put("armR",0);
                intersectionDirectionButtons.get("HE").visible = true;  // top of heart
            }
            else if(newState==1){
                meridianDirections.put("armR",1);
                intersectionDirectionButtons.get("HE").visible = true;  // top of heart
            }
            else{
                meridianDirections.put("armR",-1);
                intersectionDirectionButtons.get("HE").visible = true;  // top of heart
            }

            if(meridianPathTracker.getLast().startsWith("AR"))
                meridianPathTracker.add(meridianPathTracker.size()-1,"AR"+newState);
            else
                meridianPathTracker.add("AR"+newState);

            intersectionDirectionButtons.get(meridianPathTracker.get(meridianPathTracker.size()-2).stripTrailing()).visible = false;
        })));

        // top of heart
        intersectionDirectionButtons.put("HE",this.addDrawableChild(new IntersectionDirectionButton(k - (routingButtonSize/2) , l-48 - (routingButtonSize/2), 5, (button) -> {
            int oldState = ((IntersectionDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((IntersectionDirectionButton) button).setState(newState);

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"head") < 100) {
                newState = 0;
            }

            if(newState==0){
                meridianDirections.put("head",0);
            }
            else if(newState==1){
                meridianDirections.put("head",1);
            }
            else{
                meridianDirections.put("head",-1);
            }

            if(meridianPathTracker.get(meridianPathTracker.size()-2).equals("SHR") || meridianPathTracker.get(meridianPathTracker.size()-2).equals("AR") )
                intersectionDirectionButtons.get("SHL").visible = true;
            else
                intersectionDirectionButtons.get("SHR").visible = true;

            if(meridianPathTracker.getLast().startsWith("HE"))
                meridianPathTracker.add(meridianPathTracker.size()-1,"HE"+newState);
            else
                meridianPathTracker.add("HE"+newState);

            intersectionDirectionButtons.get(meridianPathTracker.get(meridianPathTracker.size()-2).stripTrailing()).visible = false;
        })));

        // left side of heart
        intersectionDirectionButtons.put("SHL",this.addDrawableChild(new IntersectionDirectionButton(k - (routingButtonSize/2) - 7, l-32 - (routingButtonSize/2), 5, (button) -> {
            int oldState = ((IntersectionDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((IntersectionDirectionButton) button).setState(newState);

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"shoulderL") < 100) {
                newState = 0;
            }

            if(newState==0){
                meridianDirections.put("shoulderL",0);
                intersectionDirectionButtons.get("AL").visible = false;
                intersectionDirectionButtons.get("HE").visible = false;
            }
            else if(newState==1){
                meridianDirections.put("shoulderL",1);
                intersectionDirectionButtons.get("AL").visible = true;
                intersectionDirectionButtons.get("HE").visible = false;
            }
            else{
                meridianDirections.put("shoulderL",-1);
                intersectionDirectionButtons.get("AL").visible = true;
                intersectionDirectionButtons.get("HE").visible = false;
            }

            startLocationButtons[0].visible = false;
            startLocationButtons[1].visible = false;
            startLocationButtons[2].visible = false;
            startLocationButtons[3].visible = false;

            if(meridianPathTracker.getLast().startsWith("SHL"))
                meridianPathTracker.add(meridianPathTracker.size()-1,"SHL"+newState);
            else
                meridianPathTracker.add("SHL"+newState);
        })));

        // left arm
        intersectionDirectionButtons.put("AL",this.addDrawableChild(new IntersectionDirectionButton(k - (routingButtonSize/2) - 25, l-28 - (routingButtonSize/2), 5, (button) -> {
            int oldState = ((IntersectionDirectionButton) button).getState();
            int newState = oldState + 1;
            if(newState == 3)
                newState =0;
            ((IntersectionDirectionButton) button).setState(newState);

            if(PlayerCultivationAttatchments.getSpecificMeridianProgress(player,"armL") < 100) {
                newState = 0;
            }

            if(newState==0){
                meridianDirections.put("armL",0);
                intersectionDirectionButtons.get("HE").visible = true;  // top of heart
            }
            else if(newState==1){
                meridianDirections.put("armL",1);
                intersectionDirectionButtons.get("HE").visible = true;  // top of heart
            }
            else{
                meridianDirections.put("armL",-1);
                intersectionDirectionButtons.get("HE").visible = true;  // top of heart
            }

            if(meridianPathTracker.getLast().startsWith("AL"))
                meridianPathTracker.add(meridianPathTracker.size()-1,"AL"+newState);
            else
                meridianPathTracker.add("AL"+newState);

            intersectionDirectionButtons.get(meridianPathTracker.get(meridianPathTracker.size()-2).stripTrailing()).visible = false;
        })));




    }



    @Environment(EnvType.CLIENT)
    public static class AcceptCultivationButton extends ButtonWidget {
        final int index;

        public AcceptCultivationButton(final int x, final int y, final int index, final PressAction onPress) {
            super(x, y, 135, 25, Text.translatable(CultivationMod.MOD_ID+".cultivation_screen.cultivate"), onPress, DEFAULT_NARRATION_SUPPLIER);
            this.index = index;
            this.visible = true;
        }

        public int getIndex() {
            return this.index;
        }
    }

    public static class IntersectionDirectionButton extends ButtonWidget {
        final int index;
        public int state = 0;

        public IntersectionDirectionButton(final int x, final int y, final int index, final PressAction onPress) {
            super(x, y,routingButtonSize,routingButtonSize, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
            this.index = index;
            this.visible = false;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            context.getMatrices().push();
            context.getMatrices().translate(0,0,10);
            super.renderWidget(context, mouseX, mouseY, delta);
            context.getMatrices().pop();
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

    public static class StartLocationButton extends ButtonWidget {
        final int index;
        public int state = 0;

        public StartLocationButton(final int x, final int y, final int index, final PressAction onPress) {
            super(x, y,routingButtonSize,routingButtonSize, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
            this.index = index;
            this.visible = true;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            context.getMatrices().push();
            context.getMatrices().translate(0,0,10);
            super.renderWidget(context, mouseX, mouseY, delta);
            context.getMatrices().pop();
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
