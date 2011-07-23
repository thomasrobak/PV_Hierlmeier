package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(PositionstypController)
@Mock(Positionstyp)
class PositionstypControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/positionstyp/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.positionstypInstanceList.size() == 0
        assert model.positionstypInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.positionstypInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.positionstypInstance != null
        assert view == '/positionstyp/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/positionstyp/show/1'
        assert controller.flash.message != null
        assert Positionstyp.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/positionstyp/list'


        def positionstyp = new Positionstyp()

        // TODO: populate domain properties

        assert positionstyp.save() != null

        params.id = positionstyp.id

        def model = controller.show()

        assert model.positionstypInstance == positionstyp
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/positionstyp/list'


        def positionstyp = new Positionstyp()

        // TODO: populate valid domain properties

        assert positionstyp.save() != null

        params.id = positionstyp.id

        def model = controller.edit()

        assert model.positionstypInstance == positionstyp
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/positionstyp/list'

        response.reset()


        def positionstyp = new Positionstyp()

        // TODO: populate valid domain properties

        assert positionstyp.save() != null

        // test invalid parameters in update
        params.id = positionstyp.id

        controller.update()

        assert view == "/positionstyp/edit"
        assert model.positionstypInstance != null

        positionstyp.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/positionstyp/show/$positionstyp.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/positionstyp/list'

        response.reset()

        def positionstyp = new Positionstyp()

        // TODO: populate valid domain properties
        assert positionstyp.save() != null
        assert Positionstyp.count() == 1

        params.id = positionstyp.id

        controller.delete()

        assert Positionstyp.count() == 0
        assert Positionstyp.get(positionstyp.id) == null
        assert response.redirectedUrl == '/positionstyp/list'


    }


}