package capstone.team1.eventHorizon.events.utility.fawe;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockSupport;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NaturalSculkShrieker implements SculkShrieker
{
    @Override
    public boolean isCanSummon()
    {
        return false;
    }

    @Override
    public void setCanSummon(boolean b)
    {

    }

    @Override
    public boolean isShrieking()
    {
        return false;
    }

    @Override
    public void setShrieking(boolean b)
    {

    }

    @Override
    public boolean isWaterlogged()
    {
        return false;
    }

    @Override
    public void setWaterlogged(boolean b)
    {

    }

    @Override
    public @NotNull Material getMaterial()
    {
        return null;
    }

    @Override
    public @NotNull String getAsString()
    {
        return null;
    }

    @Override
    public @NotNull String getAsString(boolean b)
    {
        return null;
    }

    @Override
    public @NotNull BlockData merge(@NotNull BlockData blockData)
    {
        return null;
    }

    @Override
    public boolean matches(@Nullable BlockData blockData)
    {
        return false;
    }

    @Override
    public @NotNull BlockData clone()
    {
        return null;
    }

    @Override
    public @NotNull SoundGroup getSoundGroup()
    {
        return null;
    }

    @Override
    public int getLightEmission()
    {
        return 0;
    }

    @Override
    public boolean isOccluding()
    {
        return false;
    }

    @Override
    public boolean requiresCorrectToolForDrops()
    {
        return false;
    }

    @Override
    public boolean isPreferredTool(@NotNull ItemStack itemStack)
    {
        return false;
    }

    @Override
    public @NotNull PistonMoveReaction getPistonMoveReaction()
    {
        return null;
    }

    @Override
    public boolean isSupported(@NotNull Block block)
    {
        return false;
    }

    @Override
    public boolean isSupported(@NotNull Location location)
    {
        return false;
    }

    @Override
    public boolean isFaceSturdy(@NotNull BlockFace blockFace, @NotNull BlockSupport blockSupport)
    {
        return false;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull Location location)
    {
        return null;
    }

    @Override
    public @NotNull Color getMapColor()
    {
        return null;
    }

    @Override
    public @NotNull Material getPlacementMaterial()
    {
        return null;
    }

    @Override
    public void rotate(@NotNull StructureRotation structureRotation)
    {

    }

    @Override
    public void mirror(@NotNull Mirror mirror)
    {

    }

    @Override
    public void copyTo(@NotNull BlockData blockData)
    {

    }

    @Override
    public @NotNull BlockState createBlockState()
    {
        return null;
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack itemStack, boolean b)
    {
        return 0;
    }

    @Override
    public boolean isRandomlyTicked()
    {
        return false;
    }
}
