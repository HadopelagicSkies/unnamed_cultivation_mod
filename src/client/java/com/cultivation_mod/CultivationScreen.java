package com.cultivation_mod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CultivationScreen extends Screen {
    private final PlayerEntity player;
    private final int titleX;
    private final int titleY;
    private final int backgroundWidth;
    private final int backgroundHeight;
    private float mouseX;
    private float mouseY;
    private boolean mousePressed = false;
    private List<int[]> tracedPath = new ArrayList<>();

    private static int drawPathAreaX =135;
    private static int drawPathAreaY =152;

    private static final Identifier BACKGROUND_TEXTURE = Identifier.of(CultivationMod.MOD_ID, "textures/gui/cultivation_screen.png");
    private static final Identifier LINE = Identifier.of(CultivationMod.MOD_ID, "textures/gui/line.png");
    private PathDrawButton pathDrawButton;

    protected CultivationScreen(PlayerEntity player) {
        super(Text.translatable("gui.cultivation_mod.cultivation_screen"));
        this.player = player;
        this.titleX = 10;
        this.titleY = 10;
        this.backgroundWidth = 276;
        this.backgroundHeight = 253;
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) /2;
        int y = (height - backgroundHeight) /2;
        context.getMatrices().push();
        context.getMatrices().translate(0,0,1f);
        context.getMatrices().pop();
        context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);
    }

    protected void drawDrawnPath(DrawContext context, float delta, int mouseX, int mouseY) {
        if (tracedPath.size() > 1) {
            for (int i = 0; i < tracedPath.size(); i++) {
                int[] prevCoords = tracedPath.get(i-1);
                int[] coords = tracedPath.get(i);
                context.drawTexture(RenderLayer::getGuiTextured, LINE, coords[0] - 1, coords[1] - 1, 0, 0, 2, 2, 2, 2);
            }
        }
        else{
            int[] coords = tracedPath.getFirst();
            context.drawTexture(RenderLayer::getGuiTextured, LINE, coords[0] - 1, coords[1] - 1, 0, 0, 2, 2, 2, 2);
        }

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

        drawDrawnPath(context,delta,mouseX,mouseY);

        if(mouseDragged(mouseX,mouseY,0,0,0)) {
            tracedPath.add(new int[]{mouseX, mouseY});
        }
        else if(mouseReleased(mouseX,mouseY,0)) {
            mousePressed = false;
        }


    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        this.pathDrawButton = this.addDrawableChild(new PathDrawButton(i + 70, j+8, 0, (button) -> {
            if (button instanceof PathDrawButton) {
                mousePressed = true;
                tracedPath = new ArrayList<>();
            }
        }));
    }



    @Environment(EnvType.CLIENT)
    public static class PathDrawButton extends ButtonWidget {
        final int index;

        public PathDrawButton(final int x, final int y, final int index, final PressAction onPress) {
            super(x, y, drawPathAreaX, drawPathAreaY, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
            this.index = index;
            this.visible = true;
        }

        public int getIndex() {
            return this.index;
        }
    }


}
