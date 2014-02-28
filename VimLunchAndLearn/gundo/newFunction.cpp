void doStuffWithDimensions(int posX, int posY, int height, int width )
{
  weightedPosition[0]=0.0;
  weightedPosition[1]=0.0;
  weightedPosition[2]=0.0;
  setPos( pos );
  sgCopyVec4( this->dimensions, dimensions );

  borders->setPos(pos);

  float redAmount = level*.10; 
  float greenAmount = (1-level*.10);
  float blueAmount = (1-level*.10);
  sgVec3 newColor = { redAmount, greenAmount, blueAmount };

  borders->setColor( newColor );
  borders->setSideLength( dimensions[0] );

  MyShape::addShapeToList( borders );

  sgVec3 CoMColor = { 0, 1, 0 };

  centerOfMassRepresentation = make_shared<Circle>();
  centerOfMassRepresentation->setPos( 0,0,0 );
  centerOfMassRepresentation->setRadius( 1.0 );
  centerOfMassRepresentation->setColor(CoMColor);

  if ( level == 1 )
  {
    MyShape::addShapeToList( centerOfMassRepresentation );
  }

}
